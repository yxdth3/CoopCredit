package com.coopcredit.credit_application.domain.port.in;

import com.coopcredit.credit_application.domain.model.Affiliate;

import java.util.List;

public interface GetAffiliateUseCase {
    Affiliate getById(Long id);
    Affiliate getByDocument(String document);
    List<Affiliate> getAll();
}
