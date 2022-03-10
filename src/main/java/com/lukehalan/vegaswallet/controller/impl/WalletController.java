package com.lukehalan.vegaswallet.controller.impl;

import com.lukehalan.vegaswallet.controller.IWalletController;
import com.lukehalan.vegaswallet.model.response.WalletResponse;
import com.lukehalan.vegaswallet.service.IWalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wallet")
@RequiredArgsConstructor
public class WalletController implements IWalletController {

    private final IWalletService iWalletService;

    @Override
    public WalletResponse getBalance(Integer playerId) {
         return iWalletService.getBalance(playerId);
    }

    @Override
    public WalletResponse getPlayerById(Integer playerId) {
        return iWalletService.findByPlayerId(playerId);
    }

    @Override
    public WalletResponse getWalletById(Integer walletId) {
        return iWalletService.findById(walletId);
    }

}
