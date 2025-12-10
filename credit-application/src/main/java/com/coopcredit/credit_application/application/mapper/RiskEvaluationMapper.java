package com.coopcredit.credit_application.application.mapper;


import com.coopcredit.credit_application.application.dto.response.RiskEvaluationResponseDTO;
import com.coopcredit.credit_application.domain.model.RiskEvaluation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RiskEvaluationMapper {

    @Mapping(target = "approvalRecommended", expression = "java(evaluation.isApprovalRecommended())")
    RiskEvaluationResponseDTO toResponseDTO(RiskEvaluation evaluation);
}