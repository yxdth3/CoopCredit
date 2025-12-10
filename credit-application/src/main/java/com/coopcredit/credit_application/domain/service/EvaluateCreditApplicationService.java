package com.coopcredit.credit_application.domain.service;

import com.coopcredit.credit_application.domain.exception.InvalidCreditApplicationException;
import com.coopcredit.credit_application.domain.model.Affiliate;
import com.coopcredit.credit_application.domain.model.CreditApplication;
import com.coopcredit.credit_application.domain.model.RiskEvaluation;
import com.coopcredit.credit_application.domain.port.in.EvaluateCreditApplicationUseCase;
import com.coopcredit.credit_application.domain.port.in.GetAffiliateUseCase;
import com.coopcredit.credit_application.domain.port.in.GetCreditApplicationUseCase;
import com.coopcredit.credit_application.domain.port.out.CreditApplicationRepositoryPort;
import com.coopcredit.credit_application.domain.port.out.RiskEvaluationRepositoryPort;
import com.coopcredit.credit_application.domain.port.out.RiskEvaluationServicePort;

public class EvaluateCreditApplicationService implements EvaluateCreditApplicationUseCase {

    private final GetCreditApplicationUseCase getCreditApplicationUseCase;
    private final GetAffiliateUseCase getAffiliateUseCase;
    private final RiskEvaluationServicePort riskEvaluationService;
    private final RiskEvaluationRepositoryPort riskEvaluationRepository;
    private final CreditApplicationRepositoryPort applicationRepository;

    private static final int MINIMUM_SCORE = 650;

    public EvaluateCreditApplicationService(
            GetCreditApplicationUseCase getCreditApplicationUseCase,
            GetAffiliateUseCase getAffiliateUseCase,
            RiskEvaluationServicePort riskEvaluationService,
            RiskEvaluationRepositoryPort riskEvaluationRepository,
            CreditApplicationRepositoryPort applicationRepository) {
        this.getCreditApplicationUseCase = getCreditApplicationUseCase;
        this.getAffiliateUseCase = getAffiliateUseCase;
        this.riskEvaluationService = riskEvaluationService;
        this.riskEvaluationRepository = riskEvaluationRepository;
        this.applicationRepository = applicationRepository;
    }

    @Override
    public CreditApplication execute(Long applicationId) {
        // Get application
        CreditApplication application = getCreditApplicationUseCase.getById(applicationId);

        // Validate application is pending
        if (!application.isPending()) {
            throw new InvalidCreditApplicationException(
                    "Application has already been evaluated");
        }

        // Get affiliate
        Affiliate affiliate = getAffiliateUseCase.getById(application.getAffiliateId());

        // Call external risk evaluation service
        RiskEvaluation riskEvaluation = riskEvaluationService.evaluateRisk(affiliate.getDocument());

        // Save risk evaluation
        RiskEvaluation savedRiskEvaluation = riskEvaluationRepository.save(riskEvaluation);

        // Build application with risk evaluation
        CreditApplication updatedApplication = CreditApplication.builder()
                .id(application.getId())
                .affiliateId(application.getAffiliateId())
                .requestedAmount(application.getRequestedAmount())
                .termMonths(application.getTermMonths())
                .purpose(application.getPurpose())
                .status(application.getStatus())
                .requestDate(application.getRequestDate())
                .evaluationDate(application.getEvaluationDate())
                .rejectionReason(application.getRejectionReason())
                .riskEvaluation(savedRiskEvaluation)
                .build();

        // Evaluate and decide
        if (riskEvaluation.getScore() >= MINIMUM_SCORE) {
            updatedApplication.approve();
        } else {
            updatedApplication.reject("Credit score below minimum required (" + MINIMUM_SCORE + ")");
        }

        // Save updated application
        return applicationRepository.save(updatedApplication);
    }
}