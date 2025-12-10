package com.coopcredit.credit_application.domain.port.out;

import com.coopcredit.credit_application.domain.model.RiskEvaluation;

import java.util.Optional;

public interface RiskEvaluationRepositoryPort {
    RiskEvaluation save(RiskEvaluation evaluation);
    Optional<RiskEvaluation> findByDocument(String document);
}
