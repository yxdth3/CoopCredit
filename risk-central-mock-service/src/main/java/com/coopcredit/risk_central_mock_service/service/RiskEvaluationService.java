package com.coopcredit.risk_central_mock_service.service;

import com.coopcredit.risk_central_mock_service.dto.RiskEvaluationRequestDTO;
import com.coopcredit.risk_central_mock_service.dto.RiskEvaluationResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@Slf4j
public class RiskEvaluationService {

    /**
     * Evaluates risk based on document number.
     * Uses document hash as seed to ensure consistent results for same document.
     */
    public RiskEvaluationResponseDTO evaluateRisk(RiskEvaluationRequestDTO request) {
        String document = request.getDocument();

        log.info("Evaluating risk for document: {}", document);

        // Use document hash as seed for consistent results
        long seed = document.hashCode();
        Random random = new Random(seed);

        // Generate score between 300 and 850 (typical credit score range)
        int score = 300 + random.nextInt(551);

        // Determine risk level based on score
        String riskLevel;
        String message;

        if (score >= 750) {
            riskLevel = "LOW";
            message = "Excellent credit profile. Low risk for credit operations.";
        } else if (score >= 650) {
            riskLevel = "MEDIUM";
            message = "Good credit profile. Moderate risk for credit operations.";
        } else {
            riskLevel = "HIGH";
            message = "Poor credit profile. High risk for credit operations.";
        }

        log.info("Risk evaluation completed - Document: {}, Score: {}, Risk: {}",
                document, score, riskLevel);

        return RiskEvaluationResponseDTO.builder()
                .document(document)
                .score(score)
                .riskLevel(riskLevel)
                .message(message)
                .build();
    }

    /**
     * Simulates different risk scenarios based on specific document patterns.
     * This is useful for testing.
     */
    public RiskEvaluationResponseDTO evaluateRiskWithScenarios(RiskEvaluationRequestDTO request) {
        String document = request.getDocument();

        log.info("Evaluating risk with scenarios for document: {}", document);

        // Special scenarios for testing
        if (document.startsWith("999")) {
            // Simulate excellent credit
            return buildResponse(document, 800, "LOW",
                    "Excellent credit profile. Low risk for credit operations.");
        } else if (document.startsWith("888")) {
            // Simulate good credit
            return buildResponse(document, 700, "MEDIUM",
                    "Good credit profile. Moderate risk for credit operations.");
        } else if (document.startsWith("777")) {
            // Simulate poor credit
            return buildResponse(document, 500, "HIGH",
                    "Poor credit profile. High risk for credit operations.");
        } else if (document.startsWith("666")) {
            // Simulate very poor credit
            return buildResponse(document, 350, "HIGH",
                    "Very poor credit profile. Very high risk for credit operations.");
        }

        // Default: use hash-based evaluation
        return evaluateRisk(request);
    }

    private RiskEvaluationResponseDTO buildResponse(String document, int score,
                                                    String riskLevel, String message) {
        log.info("Risk evaluation completed - Document: {}, Score: {}, Risk: {}",
                document, score, riskLevel);

        return RiskEvaluationResponseDTO.builder()
                .document(document)
                .score(score)
                .riskLevel(riskLevel)
                .message(message)
                .build();
    }
}