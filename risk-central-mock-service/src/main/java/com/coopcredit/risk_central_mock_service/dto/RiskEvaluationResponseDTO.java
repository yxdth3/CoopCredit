package com.coopcredit.risk_central_mock_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RiskEvaluationResponseDTO {

    private String document;
    private Integer score;
    private String riskLevel;
    private String message;
}