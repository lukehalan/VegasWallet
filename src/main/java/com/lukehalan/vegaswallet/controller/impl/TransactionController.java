package com.lukehalan.vegaswallet.controller.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lukehalan.vegaswallet.controller.ITransactionController;
import com.lukehalan.vegaswallet.model.TransactionHistory;
import com.lukehalan.vegaswallet.model.request.TransactionRequest;
import com.lukehalan.vegaswallet.service.ITransactionService;
import com.lukehalan.vegaswallet.service.schema.SchemaValidator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transaction")
@RequiredArgsConstructor
public class TransactionController implements ITransactionController {

    private final ITransactionService iTransactionService;
    private final SchemaValidator schemaValidator;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public TransactionHistory doTransaction(TransactionRequest request) {
        schemaValidator.validate(
                "transaction-schema.json", objectMapper.convertValue(request, JsonNode.class));
        iTransactionService.save(request);
        return null;
    }

    @Override
    public List<TransactionHistory> getTransactionsByPlayerId(Integer playerId) {
        return iTransactionService.findAll(playerId);
    }
}
