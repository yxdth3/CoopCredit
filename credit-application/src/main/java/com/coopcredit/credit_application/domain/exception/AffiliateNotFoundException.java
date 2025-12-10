package com.coopcredit.credit_application.domain.exception;

public class AffiliateNotFoundException extends RuntimeException {

    public AffiliateNotFoundException(Long id) {
        super("Affiliate not found with id: " + id);
    }

    public AffiliateNotFoundException(String document) {
        super("Affiliate not found with document: " + document);
    }
}