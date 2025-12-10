package com.coopcredit.credit_application.infrastructure.adapter.out.persistance.repository;


import com.coopcredit.credit_application.domain.model.CreditApplicationStatus;
import com.coopcredit.credit_application.infrastructure.adapter.out.persistance.entity.CreditApplicationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CreditApplicationJpaRepository extends JpaRepository<CreditApplicationEntity, Long> {

    @Query("SELECT ca FROM CreditApplicationEntity ca WHERE ca.affiliate.id = :affiliateId")
    List<CreditApplicationEntity> findByAffiliateId(@Param("affiliateId") Long affiliateId);

    List<CreditApplicationEntity> findByStatus(CreditApplicationStatus status);

    @Query("SELECT ca FROM CreditApplicationEntity ca " +
            "LEFT JOIN FETCH ca.affiliate " +
            "LEFT JOIN FETCH ca.riskEvaluation " +
            "WHERE ca.id = :id")
    Optional<CreditApplicationEntity> findByIdWithDetails(@Param("id") Long id);

    @Query("SELECT ca FROM CreditApplicationEntity ca " +
            "LEFT JOIN FETCH ca.affiliate " +
            "LEFT JOIN FETCH ca.riskEvaluation")
    List<CreditApplicationEntity> findAllWithDetails();
}