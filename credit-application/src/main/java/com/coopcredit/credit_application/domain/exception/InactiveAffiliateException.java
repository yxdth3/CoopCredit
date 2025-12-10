package com.coopcredit.credit_application.domain.exception;

public class InactiveAffiliateException extends RuntimeException {

    public InactiveAffiliateException(Long affiliateId) {
        super("Affiliate with id " + affiliateId + " is not active and cannot request credit");
    }
}
