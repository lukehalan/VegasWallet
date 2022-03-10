package com.lukehalan.vegaswallet.service;

import com.lukehalan.vegaswallet.model.TransactionHistory;
import com.lukehalan.vegaswallet.model.request.TransactionRequest;
import java.util.List;

public interface ITransactionService {

    TransactionHistory save(TransactionRequest transactionRequest);
    List<TransactionHistory> findAll(Integer playerId);

}
