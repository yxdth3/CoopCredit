package com.coopcredit.credit_application.infrastructure.adapter.out.persistence.adapter;


import com.coopcredit.credit_application.domain.model.CreditApplication;
import com.coopcredit.credit_application.domain.model.CreditApplicationStatus;
import com.coopcredit.credit_application.domain.port.out.CreditApplicationRepositoryPort;
import com.coopcredit.credit_application.infrastructure.adapter.out.persistence.entity.AffiliateEntity;
import com.coopcredit.credit_application.infrastructure.adapter.out.persistence.entity.CreditApplicationEntity;
import com.coopcredit.credit_application.infrastructure.adapter.out.persistence.mapper.CreditApplicationEntityMapper;
import com.coopcredit.credit_application.infrastructure.adapter.out.persistence.repository.AffiliateJpaRepository;
import com.coopcredit.credit_application.infrastructure.adapter.out.persistence.repository.CreditApplicationJpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Transactional
public class CreditApplicationRepositoryAdapter implements CreditApplicationRepositoryPort {

    private final CreditApplicationJpaRepository jpaRepository;
    private final AffiliateJpaRepository affiliateJpaRepository;
    private final CreditApplicationEntityMapper mapper;

    public CreditApplicationRepositoryAdapter(
            CreditApplicationJpaRepository jpaRepository,
            AffiliateJpaRepository affiliateJpaRepository,
            CreditApplicationEntityMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.affiliateJpaRepository = affiliateJpaRepository;
        this.mapper = mapper;
    }

    @Override
    public CreditApplication save(CreditApplication application) {
        // Get affiliate entity
        AffiliateEntity affiliateEntity = affiliateJpaRepository.findById(application.getAffiliateId())
                .orElseThrow(() -> new RuntimeException("Affiliate not found"));

        // Convert to entity
        CreditApplicationEntity entity = mapper.toEntity(application, affiliateEntity);

        // Save
        CreditApplicationEntity savedEntity = jpaRepository.save(entity);

        // Convert back to domain
        return mapper.toDomain(savedEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CreditApplication> findById(Long id) {
        return jpaRepository.findByIdWithDetails(id)
                .map(mapper::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CreditApplication> findByAffiliateId(Long affiliateId) {
        return jpaRepository.findByAffiliateId(affiliateId).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CreditApplication> findByStatus(CreditApplicationStatus status) {
        return jpaRepository.findByStatus(status).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CreditApplication> findAll() {
        return jpaRepository.findAllWithDetails().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }
}