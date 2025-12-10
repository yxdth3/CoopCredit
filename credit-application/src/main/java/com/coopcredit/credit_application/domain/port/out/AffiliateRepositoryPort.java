package com.coopcredit.credit_application.domain.port.out;

import com.coopcredit.credit_application.domain.model.Affiliate;

import java.util.List;
import java.util.Optional;

public interface AffiliateRepositoryPort {
    Affiliate save(Affiliate affiliate);
    Optional<Affiliate> findById(Long id);
    Optional<Affiliate> findByDocument(String document);
    List<Affiliate> findAll();
    boolean existsByDocument(String document);
    void deleteById(Long id);
}
