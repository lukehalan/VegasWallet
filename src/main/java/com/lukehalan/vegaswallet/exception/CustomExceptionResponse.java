package com.lukehalan.vegaswallet.exception;

import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
public class CustomExceptionResponse {

    private String description;
    private Date datetime;

}