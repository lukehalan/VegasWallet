package com.lukehalan.vegaswallet.controller;

import com.lukehalan.vegaswallet.model.TransactionHistory;
import com.lukehalan.vegaswallet.model.request.TransactionRequest;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
public interface ITransactionController {

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    TransactionHistory doTransaction(@RequestBody TransactionRequest request);

    @GetMapping("/player/{playerId}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    List<TransactionHistory> getTransactionsByPlayerId(@PathVariable Integer playerId);

}
