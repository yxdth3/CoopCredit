package com.coopcredit.credit_application.domain.exception;

/**
 * Exception thrown when risk evaluation fails
 */
public class RiskEvaluationException extends RuntimeException {

    public RiskEvaluationException(String message) {
        super(message);
    }

    public RiskEvaluationException(String message, Throwable cause) {
        super(message, cause);
    }
}
