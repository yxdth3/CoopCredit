package com.coopcredit.credit_application.domain.port.in;

import com.coopcredit.credit_application.domain.model.CreditApplication;

import java.util.List;

public interface GetCreditApplicationUseCase {
    CreditApplication getById(Long id);
    List<CreditApplication> getByAffiliateId(Long affiliateId);
    List<CreditApplication> getAllPending();
    List<CreditApplication> getAll();
}