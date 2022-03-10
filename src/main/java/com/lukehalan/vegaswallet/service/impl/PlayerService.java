package com.lukehalan.vegaswallet.service.impl;

import com.lukehalan.vegaswallet.exception.DataNotFundException;
import com.lukehalan.vegaswallet.model.Player;
import com.lukehalan.vegaswallet.model.TransactionHistory;
import com.lukehalan.vegaswallet.model.TransactionType;
import com.lukehalan.vegaswallet.model.Wallet;
import com.lukehalan.vegaswallet.model.request.PlayerRequest;
import com.lukehalan.vegaswallet.repository.PlayerRepository;
import com.lukehalan.vegaswallet.repository.TransactionHistoryRepository;
import com.lukehalan.vegaswallet.repository.WalletRepository;
import com.lukehalan.vegaswallet.service.IPlayerService;
import java.math.BigDecimal;
import java.util.Optional;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlayerService implements IPlayerService {

    private final PlayerRepository playerRepository;
    private final WalletRepository walletRepository;
    private final TransactionHistoryRepository transactionHistoryRepository;

    @Override
    public Player find(Integer id) {
        Optional<Player> player = playerRepository.findById(id);
        if (player.isPresent() && !ObjectUtils.isEmpty(player.get().getWallet())){
            player.get().getWallet().setTransactions(null);
            return player.get();
        }else {
            throw new DataNotFundException("Player not found");
        }
    }

    @Override
    @Transactional
    public Player save(PlayerRequest player) {
        Player p = new Player(player.getFirstname(),player.getLastname());
        Wallet w = new Wallet(p, ObjectUtils.isEmpty(player.getCredit()) ? new BigDecimal(0) : player.getCredit());

        playerRepository.save(p);
        walletRepository.save(w);

        if (!ObjectUtils.isEmpty(player.getCredit())) {
            TransactionHistory t = new TransactionHistory();
            t.setPlayer(p);
            t.setWallet(w);
            t.setTransactionId(RandomStringUtils.random(15,true,true));
            t.setAmount(player.getCredit());
            t.setType(TransactionType.CREDIT.value());
            transactionHistoryRepository.save(t);
        }
        return p;
    }
}
