package com.lukehalan.vegaswallet.model.request;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerRequest {

    private String firstname;
    private String lastname;
    private BigDecimal credit;

}
