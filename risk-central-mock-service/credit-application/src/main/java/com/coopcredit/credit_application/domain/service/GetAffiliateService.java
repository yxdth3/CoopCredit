package com.coopcredit.credit_application.domain.service;

import com.coopcredit.credit_application.domain.exception.AffiliateNotFoundException;
import com.coopcredit.credit_application.domain.model.Affiliate;
import com.coopcredit.credit_application.domain.port.in.GetAffiliateUseCase;
import com.coopcredit.credit_application.domain.port.out.AffiliateRepositoryPort;

import java.util.List;

public class GetAffiliateService implements GetAffiliateUseCase {

    private final AffiliateRepositoryPort affiliateRepository;

    public GetAffiliateService(AffiliateRepositoryPort affiliateRepository) {
        this.affiliateRepository = affiliateRepository;
    }

    @Override
    public Affiliate getById(Long id) {
        return affiliateRepository.findById(id)
                .orElseThrow(() -> new AffiliateNotFoundException(id));
    }

    @Override
    public Affiliate getByDocument(String document) {
        return affiliateRepository.findByDocument(document)
                .orElseThrow(() -> new AffiliateNotFoundException(document));
    }

    @Override
    public List<Affiliate> getAll() {
        return affiliateRepository.findAll();
    }
}