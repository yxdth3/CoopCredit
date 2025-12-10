package com.coopcredit.credit_application.infrastructure.adapter.out.persistance.repository;

import com.coopcredit.credit_application.infrastructure.adapter.out.persistance.entity.RiskEvaluationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RiskEvaluationJpaRepository extends JpaRepository<RiskEvaluationEntity, Long> {

    Optional<RiskEvaluationEntity> findByDocument(String document);
}