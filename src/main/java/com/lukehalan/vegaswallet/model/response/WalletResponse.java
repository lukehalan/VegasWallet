package com.lukehalan.vegaswallet.model.response;

import java.math.BigDecimal;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WalletResponse {

    private Integer id;
    private Integer playerId;
    private BigDecimal balance;
    private Date lastUpdate;

    public WalletResponse(BigDecimal balance) {
        this.balance = balance;
    }
}
