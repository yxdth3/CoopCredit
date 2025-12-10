package com.coopcredit.risk_central_mock_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RiskEvaluationRequestDTO {

    @NotBlank(message = "Document is required")
    @Pattern(regexp = "^[0-9]{6,20}$", message = "Document must contain only numbers and be between 6 and 20 digits")
    private String document;
}