package com.coopcredit.credit_application.infrastructure.config;

import com.coopcredit.credit_application.domain.port.in.*;
        import com.coopcredit.credit_application.domain.port.out.*;
        import com.coopcredit.credit_application.domain.service.*;
        import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    // Affiliate Use Cases
    @Bean
    public RegisterAffiliateUseCase registerAffiliateUseCase(
            AffiliateRepositoryPort affiliateRepository) {
        return new RegisterAffiliateService(affiliateRepository);
    }

    @Bean
    public GetAffiliateUseCase getAffiliateUseCase(
            AffiliateRepositoryPort affiliateRepository) {
        return new GetAffiliateService(affiliateRepository);
    }

    // Credit Application Use Cases
    @Bean
    public RegisterCreditApplicationUseCase registerCreditApplicationUseCase(
            CreditApplicationRepositoryPort applicationRepository,
            GetAffiliateUseCase getAffiliateUseCase) {
        return new RegisterCreditApplicationService(applicationRepository, getAffiliateUseCase);
    }

    @Bean
    public GetCreditApplicationUseCase getCreditApplicationUseCase(
            CreditApplicationRepositoryPort applicationRepository) {
        return new GetCreditApplicationService(applicationRepository);
    }

    @Bean
    public EvaluateCreditApplicationUseCase evaluateCreditApplicationUseCase(
            GetCreditApplicationUseCase getCreditApplicationUseCase,
            GetAffiliateUseCase getAffiliateUseCase,
            RiskEvaluationServicePort riskEvaluationService,
            RiskEvaluationRepositoryPort riskEvaluationRepository,
            CreditApplicationRepositoryPort applicationRepository) {
        return new EvaluateCreditApplicationService(
                getCreditApplicationUseCase,
                getAffiliateUseCase,
                riskEvaluationService,
                riskEvaluationRepository,
                applicationRepository);
    }
}