package com.coopcredit.credit_application.domain.service;


import com.coopcredit.credit_application.domain.exception.InactiveAffiliateException;
import com.coopcredit.credit_application.domain.exception.InvalidCreditApplicationException;
import com.coopcredit.credit_application.domain.model.Affiliate;
import com.coopcredit.credit_application.domain.model.CreditApplication;
import com.coopcredit.credit_application.domain.model.CreditApplicationStatus;
import com.coopcredit.credit_application.domain.port.in.GetAffiliateUseCase;
import com.coopcredit.credit_application.domain.port.in.RegisterCreditApplicationUseCase;
import com.coopcredit.credit_application.domain.port.out.CreditApplicationRepositoryPort;

import java.time.LocalDateTime;

public class RegisterCreditApplicationService implements RegisterCreditApplicationUseCase {

    private final CreditApplicationRepositoryPort applicationRepository;
    private final GetAffiliateUseCase getAffiliateUseCase;

    private static final int MINIMUM_SENIORITY_MONTHS = 3;
    private static final double MAX_DEBT_TO_INCOME_RATIO = 40.0;

    public RegisterCreditApplicationService(
            CreditApplicationRepositoryPort applicationRepository,
            GetAffiliateUseCase getAffiliateUseCase) {
        this.applicationRepository = applicationRepository;
        this.getAffiliateUseCase = getAffiliateUseCase;
    }

    @Override
    public CreditApplication execute(CreditApplication application) {
        // Get and validate affiliate
        Affiliate affiliate = getAffiliateUseCase.getById(application.getAffiliateId());

        // Validate affiliate is active
        if (!affiliate.isActive()) {
            throw new InactiveAffiliateException(affiliate.getId());
        }

        // Validate affiliate can request credit
        if (!affiliate.canRequestCredit()) {
            throw new InvalidCreditApplicationException(
                    "Affiliate does not meet the requirements to request credit");
        }

        // Validate minimum seniority
        if (!affiliate.hasMinimumSeniority(MINIMUM_SENIORITY_MONTHS)) {
            throw new InvalidCreditApplicationException(
                    "Affiliate must have at least " + MINIMUM_SENIORITY_MONTHS +
                            " months of seniority");
        }

        // Validate requested amount
        if (application.getRequestedAmount() <= 0) {
            throw new InvalidCreditApplicationException("Requested amount must be greater than 0");
        }

        Double maxAmount = affiliate.getMaxCreditAmount();
        if (application.getRequestedAmount() > maxAmount) {
            throw new InvalidCreditApplicationException(
                    "Requested amount exceeds maximum allowed (" + maxAmount + ")");
        }

        // Validate term
        if (application.getTermMonths() == null || application.getTermMonths() <= 0) {
            throw new InvalidCreditApplicationException("Term must be greater than 0");
        }

        if (application.getTermMonths() > 60) {
            throw new InvalidCreditApplicationException("Term cannot exceed 60 months");
        }

        // Validate debt-to-income ratio
        Double debtRatio = application.calculateDebtToIncomeRatio(affiliate.getSalary());
        if (debtRatio > MAX_DEBT_TO_INCOME_RATIO) {
            throw new InvalidCreditApplicationException(
                    "Debt-to-income ratio (" + String.format("%.2f", debtRatio) +
                            "%) exceeds maximum allowed (" + MAX_DEBT_TO_INCOME_RATIO + "%)");
        }

        // Create application
        CreditApplication applicationToSave = CreditApplication.builder()
                .affiliateId(application.getAffiliateId())
                .requestedAmount(application.getRequestedAmount())
                .termMonths(application.getTermMonths())
                .purpose(application.getPurpose())
                .status(CreditApplicationStatus.PENDING)
                .requestDate(LocalDateTime.now())
                .build();

        return applicationRepository.save(applicationToSave);
    }
}