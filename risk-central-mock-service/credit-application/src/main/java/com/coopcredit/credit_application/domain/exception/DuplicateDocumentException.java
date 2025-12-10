package com.coopcredit.credit_application.domain.exception;


public class DuplicateDocumentException extends RuntimeException {

    public DuplicateDocumentException(String document) {
        super("Affiliate with document " + document + " already exists");
    }
}