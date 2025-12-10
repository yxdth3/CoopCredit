package com.coopcredit.credit_application.infrastructure.adapter.out.persistance.mapper;

import com.coopcredit.credit_application.domain.model.CreditApplication;
import com.coopcredit.credit_application.infrastructure.adapter.out.persistance.entity.AffiliateEntity;
import com.coopcredit.credit_application.infrastructure.adapter.out.persistance.entity.CreditApplicationEntity;
import com.coopcredit.credit_application.infrastructure.adapter.out.persistance.entity.RiskEvaluationEntity;
import org.springframework.stereotype.Component;

@Component
public class CreditApplicationEntityMapper {

    private final RiskEvaluationEntityMapper riskMapper;

    public CreditApplicationEntityMapper(RiskEvaluationEntityMapper riskMapper) {
        this.riskMapper = riskMapper;
    }

    public CreditApplication toDomain(CreditApplicationEntity entity) {
        if (entity == null) return null;

        return CreditApplication.builder()
                .id(entity.getId())
                .affiliateId(entity.getAffiliate() != null ? entity.getAffiliate().getId() : null)
                .requestedAmount(entity.getRequestedAmount())
                .termMonths(entity.getTermMonths())
                .purpose(entity.getPurpose())
                .status(entity.getStatus())
                .requestDate(entity.getRequestDate())
                .evaluationDate(entity.getEvaluationDate())
                .rejectionReason(entity.getRejectionReason())
                .riskEvaluation(riskMapper.toDomain(entity.getRiskEvaluation()))
                .build();
    }

    public CreditApplicationEntity toEntity(CreditApplication domain, AffiliateEntity affiliateEntity) {
        if (domain == null) return null;

        CreditApplicationEntity entity = CreditApplicationEntity.builder()
                .id(domain.getId())
                .affiliate(affiliateEntity)
                .requestedAmount(domain.getRequestedAmount())
                .termMonths(domain.getTermMonths())
                .purpose(domain.getPurpose())
                .status(domain.getStatus())
                .requestDate(domain.getRequestDate())
                .evaluationDate(domain.getEvaluationDate())
                .rejectionReason(domain.getRejectionReason())
                .build();

        if (domain.getRiskEvaluation() != null) {
            RiskEvaluationEntity riskEntity = riskMapper.toEntity(domain.getRiskEvaluation());
            riskEntity.setCreditApplication(entity);
            entity.setRiskEvaluation(riskEntity);
        }

        return entity;
    }
}