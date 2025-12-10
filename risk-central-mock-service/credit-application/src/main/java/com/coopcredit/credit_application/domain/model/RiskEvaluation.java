package com.coopcredit.credit_application.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RiskEvaluation {

    private Long id;
    private String document;
    private Integer score;
    private RiskEvaluationStatus riskEvaluationStatus;
    private LocalDateTime evaluationDate;

    // Business logic methods
    public boolean isLowRisk() {
        return this.riskEvaluationStatus == RiskEvaluationStatus.LOW;
    }

    public boolean isMediumRisk() {
        return this.riskEvaluationStatus == RiskEvaluationStatus.MEDIUM;
    }

    public boolean isHighRisk() {
        return this.riskEvaluationStatus == RiskEvaluationStatus.HIGH;
    }

    public boolean isApprovalRecommended() {
        return score != null && score >= 650;
    }
}