package com.lukehalan.vegaswallet.model.request;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequest {

    private Integer playerId;
    private BigDecimal amount;
    private String transactionId;
    private String type;

}
