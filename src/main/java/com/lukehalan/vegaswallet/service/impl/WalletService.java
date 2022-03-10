package com.lukehalan.vegaswallet.service.impl;

import com.lukehalan.vegaswallet.model.Player;
import com.lukehalan.vegaswallet.model.Wallet;
import com.lukehalan.vegaswallet.model.response.WalletResponse;
import com.lukehalan.vegaswallet.repository.WalletRepository;
import com.lukehalan.vegaswallet.service.IWalletService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WalletService implements IWalletService {

  private final WalletRepository walletRepository;
  private final ModelMapper modelMapper;

  @Override
  public WalletResponse getBalance(Integer playerId) {
    WalletResponse response = null;
    Optional<Wallet> wallet = walletRepository.findById(playerId);
    if (wallet.isPresent()) {
      response = new WalletResponse(wallet.get().getBalance());
    }
    return response;
  }

  @Override
  public WalletResponse findByPlayerId(Integer playerId) {
    Optional<Wallet> wallet = walletRepository.findByPlayerId(new Player(playerId));
    return modelMapper.map(wallet.get(),WalletResponse.class);
  }

  @Override
  public WalletResponse findById(Integer id) {
    Optional<Wallet> wallet = walletRepository.findById(id);
    return modelMapper.map(wallet.get(),WalletResponse.class);
  }

}
