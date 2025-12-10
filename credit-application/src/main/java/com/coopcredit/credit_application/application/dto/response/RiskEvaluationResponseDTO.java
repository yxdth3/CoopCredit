package com.coopcredit.credit_application.application.dto.response;


import com.coopcredit.credit_application.domain.model.RiskEvaluationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RiskEvaluationResponseDTO {

    private Long id;
    private String document;
    private Integer score;
    private RiskEvaluationStatus riskEvaluationStatus;
    private LocalDateTime evaluationDate;
    private Boolean approvalRecommended;
}