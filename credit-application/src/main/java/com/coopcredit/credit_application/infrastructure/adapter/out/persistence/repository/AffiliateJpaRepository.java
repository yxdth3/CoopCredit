package com.coopcredit.credit_application.infrastructure.adapter.out.persistence.repository;

import com.coopcredit.credit_application.infrastructure.adapter.out.persistence.entity.AffiliateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AffiliateJpaRepository extends JpaRepository<AffiliateEntity, Long> {

    Optional<AffiliateEntity> findByDocument(String document);

    boolean existsByDocument(String document);

    @Query("SELECT a FROM AffiliateEntity a LEFT JOIN FETCH a.creditApplications WHERE a.id = :id")
    Optional<AffiliateEntity> findByIdWithApplications(@Param("id") Long id);
}