package com.coopcredit.credit_application.domain.port.out;

import com.coopcredit.credit_application.domain.model.RiskEvaluation;

public interface RiskEvaluationServicePort {
    RiskEvaluation evaluateRisk(String document);

}
