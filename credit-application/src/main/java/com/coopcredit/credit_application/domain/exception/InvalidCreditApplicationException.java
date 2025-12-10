package com.coopcredit.credit_application.domain.exception;

public class InvalidCreditApplicationException extends RuntimeException {

    public InvalidCreditApplicationException(String message) {
        super(message);
    }
}