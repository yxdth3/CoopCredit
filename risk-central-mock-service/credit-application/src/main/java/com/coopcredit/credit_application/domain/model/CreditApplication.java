package com.coopcredit.credit_application.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class CreditApplication {
    private Long id;
    private Long affiliateId;
    private Double requestedAmount;
    private Integer termMonths;
    private String purpose;
    private CreditApplicationStatus status;
    private LocalDateTime requestDate;
    private LocalDateTime evaluationDate;
    private String rejectionReason;
    private RiskEvaluation riskEvaluation;

    // Business logic methods
    public boolean isPending() {
        return this.status == CreditApplicationStatus.PENDING;
    }

    public boolean isApproved() {
        return this.status == CreditApplicationStatus.APPROVED;
    }

    public void approve() {
        this.status = CreditApplicationStatus.APPROVED;
        this.evaluationDate = LocalDateTime.now();
    }

    public void reject(String reason) {
        this.status = CreditApplicationStatus.REJECTED;
        this.evaluationDate = LocalDateTime.now();
        this.rejectionReason = reason;
    }

    public Double calculateMonthlyPayment() {
        if (requestedAmount == null || termMonths == null || termMonths == 0) {
            return 0.0;
        }
        // Simple calculation (without interest for now)
        return requestedAmount / termMonths;
    }

    public Double calculateDebtToIncomeRatio(Double affiliateSalary) {
        if (affiliateSalary == null || affiliateSalary == 0) {
            return 100.0;
        }
        return (calculateMonthlyPayment() / affiliateSalary) * 100;
    }
}
