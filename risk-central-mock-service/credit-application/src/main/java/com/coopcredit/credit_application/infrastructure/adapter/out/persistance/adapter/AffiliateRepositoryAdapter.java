package com.coopcredit.credit_application.infrastructure.adapter.out.persistance.adapter;

import com.coopcredit.credit_application.domain.model.Affiliate;
import com.coopcredit.credit_application.domain.port.out.AffiliateRepositoryPort;
import com.coopcredit.credit_application.infrastructure.adapter.out.persistance.entity.AffiliateEntity;
import com.coopcredit.credit_application.infrastructure.adapter.out.persistance.mapper.AffiliateEntityMapper;
import com.coopcredit.credit_application.infrastructure.adapter.out.persistance.repository.AffiliateJpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Transactional
public class AffiliateRepositoryAdapter implements AffiliateRepositoryPort {

    private final AffiliateJpaRepository jpaRepository;
    private final AffiliateEntityMapper mapper;

    public AffiliateRepositoryAdapter(
            AffiliateJpaRepository jpaRepository,
            AffiliateEntityMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Affiliate save(Affiliate affiliate) {
        AffiliateEntity entity = mapper.toEntity(affiliate);
        AffiliateEntity savedEntity = jpaRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Affiliate> findById(Long id) {
        return jpaRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Affiliate> findByDocument(String document) {
        return jpaRepository.findByDocument(document)
                .map(mapper::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Affiliate> findAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByDocument(String document) {
        return jpaRepository.existsByDocument(document);
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }
}