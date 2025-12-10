package com.coopcredit.credit_application.infrastructure.adapter.out.persistance.mapper;

import com.coopcredit.credit_application.domain.model.Affiliate;
import com.coopcredit.credit_application.infrastructure.adapter.out.persistance.entity.AffiliateEntity;
import org.springframework.stereotype.Component;

@Component
public class AffiliateEntityMapper {

    public Affiliate toDomain(AffiliateEntity entity) {
        if (entity == null) return null;

        return Affiliate.builder()
                .id(entity.getId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .document(entity.getDocument())
                .email(entity.getEmail())
                .phone(entity.getPhone())
                .salary(entity.getSalary())
                .affiliationDate(entity.getAffiliationDate())
                .status(entity.getStatus())
                .build();
    }

    public AffiliateEntity toEntity(Affiliate domain) {
        if (domain == null) return null;

        return AffiliateEntity.builder()
                .id(domain.getId())
                .firstName(domain.getFirstName())
                .lastName(domain.getLastName())
                .document(domain.getDocument())
                .email(domain.getEmail())
                .phone(domain.getPhone())
                .salary(domain.getSalary())
                .affiliationDate(domain.getAffiliationDate())
                .status(domain.getStatus())
                .build();
    }
}