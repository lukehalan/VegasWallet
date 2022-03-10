package com.lukehalan.vegaswallet.service;

import com.lukehalan.vegaswallet.model.response.WalletResponse;

public interface IWalletService {

    WalletResponse getBalance(Integer playerId);
    WalletResponse findByPlayerId(Integer playerId);
    WalletResponse findById(Integer id);

}
