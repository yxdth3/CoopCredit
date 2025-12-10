package com.coopcredit.credit_application.infrastructure.adapter.out.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RiskResponseDTO {
    private String document;
    private Integer score;
    private String riskLevel;
}