package com.coopcredit.credit_application.infrastructure.adapter.out.persistance.mapper;

import com.coopcredit.credit_application.domain.model.RiskEvaluation;
import com.coopcredit.credit_application.infrastructure.adapter.out.persistance.entity.RiskEvaluationEntity;
import org.springframework.stereotype.Component;

@Component
public class RiskEvaluationEntityMapper {

    public RiskEvaluation toDomain(RiskEvaluationEntity entity) {
        if (entity == null) return null;

        return RiskEvaluation.builder()
                .id(entity.getId())
                .document(entity.getDocument())
                .score(entity.getScore())
                .riskEvaluationStatus(entity.getRiskEvaluationStatus())
                .evaluationDate(entity.getEvaluationDate())
                .build();
    }

    public RiskEvaluationEntity toEntity(RiskEvaluation domain) {
        if (domain == null) return null;

        return RiskEvaluationEntity.builder()
                .id(domain.getId())
                .document(domain.getDocument())
                .score(domain.getScore())
                .riskEvaluationStatus(domain.getRiskEvaluationStatus())
                .evaluationDate(domain.getEvaluationDate())
                .build();
    }
}