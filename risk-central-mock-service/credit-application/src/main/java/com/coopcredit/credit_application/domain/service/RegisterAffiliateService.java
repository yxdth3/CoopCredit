package com.coopcredit.credit_application.domain.service;

import com.coopcredit.credit_application.domain.exception.DuplicateDocumentException;
import com.coopcredit.credit_application.domain.model.Affiliate;
import com.coopcredit.credit_application.domain.model.AffiliateStatus;
import com.coopcredit.credit_application.domain.port.in.RegisterAffiliateUseCase;
import com.coopcredit.credit_application.domain.port.out.AffiliateRepositoryPort;

import java.time.LocalDate;

public class RegisterAffiliateService implements RegisterAffiliateUseCase {

    private final AffiliateRepositoryPort affiliateRepository;

    public RegisterAffiliateService(AffiliateRepositoryPort affiliateRepository) {
        this.affiliateRepository = affiliateRepository;
    }

    @Override
    public Affiliate execute(Affiliate affiliate) {
        // Validate document is unique
        if (affiliateRepository.existsByDocument(affiliate.getDocument())) {
            throw new DuplicateDocumentException(affiliate.getDocument());
        }

        // Set default values
        Affiliate affiliateToSave = Affiliate.builder()
                .firstName(affiliate.getFirstName())
                .lastName(affiliate.getLastName())
                .document(affiliate.getDocument())
                .email(affiliate.getEmail())
                .phone(affiliate.getPhone())
                .salary(affiliate.getSalary())
                .affiliationDate(LocalDate.now())
                .status(AffiliateStatus.ACTIVE)
                .build();

        return affiliateRepository.save(affiliateToSave);
    }
}