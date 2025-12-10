package com.coopcredit.credit_application.domain.service;

import com.coopcredit.credit_application.domain.exception.InvalidCreditApplicationException;
import com.coopcredit.credit_application.domain.model.CreditApplication;
import com.coopcredit.credit_application.domain.model.CreditApplicationStatus;
import com.coopcredit.credit_application.domain.port.in.GetCreditApplicationUseCase;
import com.coopcredit.credit_application.domain.port.out.CreditApplicationRepositoryPort;

import java.util.List;

public class GetCreditApplicationService implements GetCreditApplicationUseCase {

    private final CreditApplicationRepositoryPort applicationRepository;

    public GetCreditApplicationService(CreditApplicationRepositoryPort applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    @Override
    public CreditApplication getById(Long id) {
        return applicationRepository.findById(id)
                .orElseThrow(() -> new InvalidCreditApplicationException(
                        "Credit application not found with id: " + id));
    }

    @Override
    public List<CreditApplication> getByAffiliateId(Long affiliateId) {
        return applicationRepository.findByAffiliateId(affiliateId);
    }

    @Override
    public List<CreditApplication> getAllPending() {
        return applicationRepository.findByStatus(CreditApplicationStatus.PENDING);
    }

    @Override
    public List<CreditApplication> getAll() {
        return applicationRepository.findAll();
    }
}