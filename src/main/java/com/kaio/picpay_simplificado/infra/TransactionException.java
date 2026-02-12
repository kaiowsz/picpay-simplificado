package com.kaio.picpay_simplificado.infra;

public class TransactionException extends RuntimeException {
    public TransactionException(String message) {
        super(message);
    }
}
