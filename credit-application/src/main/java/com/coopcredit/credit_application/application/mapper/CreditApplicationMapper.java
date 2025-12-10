package com.coopcredit.credit_application.application.mapper;


import com.coopcredit.credit_application.application.dto.request.CreditApplicationRequestDTO;
import com.coopcredit.credit_application.application.dto.response.CreditApplicationResponseDTO;
import com.coopcredit.credit_application.domain.model.CreditApplication;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {RiskEvaluationMapper.class})
public interface CreditApplicationMapper {

    // Request DTO to Domain
    CreditApplication toDomain(CreditApplicationRequestDTO dto);

    // Domain to Response DTO
    @Mapping(target = "affiliateName", ignore = true)
    @Mapping(target = "monthlyPayment", expression = "java(application.calculateMonthlyPayment())")
    CreditApplicationResponseDTO toResponseDTO(CreditApplication application);

    List<CreditApplicationResponseDTO> toResponseDTOList(List<CreditApplication> applications);
}