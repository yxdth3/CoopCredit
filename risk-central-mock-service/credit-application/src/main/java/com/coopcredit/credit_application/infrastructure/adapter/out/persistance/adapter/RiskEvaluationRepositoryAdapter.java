package com.coopcredit.credit_application.infrastructure.adapter.out.persistance.adapter;

import com.coopcredit.credit_application.domain.model.RiskEvaluation;
import com.coopcredit.credit_application.domain.port.out.RiskEvaluationRepositoryPort;
import com.coopcredit.credit_application.infrastructure.adapter.out.persistance.entity.RiskEvaluationEntity;
import com.coopcredit.credit_application.infrastructure.adapter.out.persistance.mapper.RiskEvaluationEntityMapper;
import com.coopcredit.credit_application.infrastructure.adapter.out.persistance.repository.RiskEvaluationJpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@Transactional
public class RiskEvaluationRepositoryAdapter implements RiskEvaluationRepositoryPort {

    private final RiskEvaluationJpaRepository jpaRepository;
    private final RiskEvaluationEntityMapper mapper;

    public RiskEvaluationRepositoryAdapter(
            RiskEvaluationJpaRepository jpaRepository,
            RiskEvaluationEntityMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public RiskEvaluation save(RiskEvaluation evaluation) {
        RiskEvaluationEntity entity = mapper.toEntity(evaluation);
        RiskEvaluationEntity savedEntity = jpaRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RiskEvaluation> findByDocument(String document) {
        return jpaRepository.findByDocument(document)
                .map(mapper::toDomain);
    }
}