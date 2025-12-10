package com.coopcredit.credit_application.application.dto.request;

import jakarta.validation.constraints.*;
        import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreditApplicationRequestDTO {

    @NotNull(message = "Affiliate ID is required")
    @Positive(message = "Affiliate ID must be positive")
    private Long affiliateId;

    @NotNull(message = "Requested amount is required")
    @DecimalMin(value = "100000", message = "Requested amount must be at least 100,000")
    @DecimalMax(value = "50000000", message = "Requested amount cannot exceed 50,000,000")
    private Double requestedAmount;

    @NotNull(message = "Term in months is required")
    @Min(value = 1, message = "Term must be at least 1 month")
    @Max(value = 60, message = "Term cannot exceed 60 months")
    private Integer termMonths;

    @Size(max = 500, message = "Purpose cannot exceed 500 characters")
    private String purpose;
}