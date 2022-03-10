package com.lukehalan.vegaswallet.controller;

import com.lukehalan.vegaswallet.model.response.WalletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Validated
public interface IWalletController {


  @GetMapping("/balance/{playerId}")
  @ResponseBody
  @ResponseStatus(HttpStatus.OK)
  WalletResponse getBalance(@PathVariable Integer playerId);

  @GetMapping("/player/{playerId}")
  @ResponseBody
  @ResponseStatus(HttpStatus.OK)
  WalletResponse getPlayerById(@PathVariable Integer playerId);

  @GetMapping("/{walletId}")
  @ResponseBody
  @ResponseStatus(HttpStatus.OK)
  WalletResponse getWalletById(@PathVariable Integer walletId);

}
