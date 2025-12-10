package com.coopcredit.credit_application.application.mapper;


import com.coopcredit.credit_application.application.dto.request.AffiliateRequestDTO;
import com.coopcredit.credit_application.application.dto.response.AffiliateResponseDTO;
import com.coopcredit.credit_application.domain.model.Affiliate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AffiliateMapper {

    // Request DTO to Domain
    Affiliate toDomain(AffiliateRequestDTO dto);

    // Domain to Response DTO
    @Mapping(target = "fullName", expression = "java(affiliate.getFullName())")
    @Mapping(target = "maxCreditAmount", expression = "java(affiliate.getMaxCreditAmount())")
    @Mapping(target = "canRequestCredit", expression = "java(affiliate.canRequestCredit())")
    AffiliateResponseDTO toResponseDTO(Affiliate affiliate);

    List<AffiliateResponseDTO> toResponseDTOList(List<Affiliate> affiliates);
}