package com.lukehalan.vegaswallet.model;

public enum TransactionType {
    CREDIT(1),
    DEBIT(2),
    WITHDRAW(3);

    private int type;
    TransactionType(int type) {
        this.type = type;
    }
    public Integer value() {
        return type;
    }
}
