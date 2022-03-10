package com.lukehalan.vegaswallet.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
public class WalletException extends RuntimeException{
    public WalletException(String message) {
        super(message);
    }
}
