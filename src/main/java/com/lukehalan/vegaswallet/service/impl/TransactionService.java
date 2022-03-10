package com.lukehalan.vegaswallet.service.impl;

import com.lukehalan.vegaswallet.exception.DataNotFundException;
import com.lukehalan.vegaswallet.exception.JsonValidationException;
import com.lukehalan.vegaswallet.exception.TransactionException;
import com.lukehalan.vegaswallet.exception.ValidationException;
import com.lukehalan.vegaswallet.model.Player;
import com.lukehalan.vegaswallet.model.TransactionHistory;
import com.lukehalan.vegaswallet.model.TransactionType;
import com.lukehalan.vegaswallet.model.Wallet;
import com.lukehalan.vegaswallet.model.request.TransactionRequest;
import com.lukehalan.vegaswallet.repository.PlayerRepository;
import com.lukehalan.vegaswallet.repository.TransactionHistoryRepository;
import com.lukehalan.vegaswallet.repository.WalletRepository;
import com.lukehalan.vegaswallet.service.ITransactionService;
import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionService implements ITransactionService {

  private final TransactionHistoryRepository transactionHistoryRepository;
  private final PlayerRepository playerRepository;
  private final WalletRepository walletRepository;

  private TransactionHistory transactionHistory;
  private Optional<Wallet> wallet;

  @Override
  @Transactional
  public TransactionHistory save(TransactionRequest request) {
    if (request.getAmount().equals(BigDecimal.ZERO)){
      throw new TransactionException("Transaction amount can't be 0");
    }
    transactionHistory = new TransactionHistory();
    Optional<TransactionHistory> history =
        transactionHistoryRepository.findByTransactionId(request.getTransactionId());
    if (history.isEmpty()) {
      Optional<Player> player = playerRepository.findById(request.getPlayerId());
      if (player.isPresent()) {
        wallet = walletRepository.findByPlayerId(player.get());
        if (wallet.isPresent()) {
            setTypeAndBalance(request);
            walletRepository.save(wallet.get());
            transactionHistory.setWallet(wallet.get());
            transactionHistory.setPlayer(player.get());
            transactionHistory.setAmount(request.getAmount());
            transactionHistory.setTransactionId(request.getTransactionId());
            transactionHistory = transactionHistoryRepository.save(transactionHistory);
        } else {
          log.error(
              "Wallet does not exist - Requested Wallet {} , Player ID: {} ",
              player.get().getWallet().getId(),
              player.get().getId());
          throw new DataNotFundException("Wallet does not exist - contact customer support");
        }
      } else {
        throw new DataNotFundException("Player does not exist");
      }
    } else {
      throw new TransactionException("Provided transaction id is already exist");
    }
    return transactionHistory;
  }

  @Override
  public List<TransactionHistory> findAll(Integer playerId) {
    List<TransactionHistory> transactions = transactionHistoryRepository.findByPlayerId(playerId);
    if (transactions.size() > 0) {
      return transactionHistoryRepository.findByPlayerId(playerId);
    } else {
      throw new DataNotFundException("No transaction found");
    }
  }


  /**
   * This method do following operations:
   *
   * set new balance in the wallet
   * set transaction type
   *
   * @param request requested object
   */
  private void setTypeAndBalance(TransactionRequest request) {
    switch (request.getType().toUpperCase(Locale.ROOT)) {
      case "CREDIT":
        wallet.get().setBalance(wallet.get().getBalance().add(request.getAmount()));
        transactionHistory.setType(TransactionType.CREDIT.value());
        break;
      case "DEBIT":
        checkBalance(wallet.get().getBalance(),request.getAmount());
        transactionHistory.setType(TransactionType.DEBIT.value());
        break;
      case "WITHDRAW":
        checkBalance(wallet.get().getBalance(),request.getAmount());
        transactionHistory.setType(TransactionType.WITHDRAW.value());
        break;
      default:
        throw new ValidationException("Transaction type is wrong");
    }
  }

  /**
   * Compare existing balance against requested amount
   * if the balance is enough it will calculate new balance
   *
   * @param actualAmount requested object
   * @param newAmount requested object
   * @throws ValidationException if balance is less than requested amount
   */
  private void checkBalance(BigDecimal actualAmount, BigDecimal newAmount) {
    if (actualAmount.compareTo(newAmount) > 0) {
      wallet.get().setBalance(actualAmount.subtract(newAmount));
    }else {
      throw new ValidationException("You don't have enough balance");
    }
  }
}
