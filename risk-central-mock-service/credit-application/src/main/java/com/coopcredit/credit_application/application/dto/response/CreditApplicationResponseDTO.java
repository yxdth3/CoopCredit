package com.coopcredit.credit_application.application.dto.response;


import com.coopcredit.credit_application.domain.model.CreditApplicationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreditApplicationResponseDTO {

    private Long id;
    private Long affiliateId;
    private String affiliateName;
    private Double requestedAmount;
    private Integer termMonths;
    private Double monthlyPayment;
    private String purpose;
    private CreditApplicationStatus status;
    private LocalDateTime requestDate;
    private LocalDateTime evaluationDate;
    private String rejectionReason;
    private RiskEvaluationResponseDTO riskEvaluation;
}