package com.coopcredit.credit_application.domain.port.out;

import com.coopcredit.credit_application.domain.model.CreditApplication;
import com.coopcredit.credit_application.domain.model.CreditApplicationStatus;

import java.util.List;
import java.util.Optional;

public interface CreditApplicationRepositoryPort {
    CreditApplication save(CreditApplication application);
    Optional<CreditApplication> findById(Long id);
    List<CreditApplication> findByAffiliateId(Long affiliateId);
    List<CreditApplication> findByStatus(CreditApplicationStatus status);
    List<CreditApplication> findAll();
    void deleteById(Long id);
}
