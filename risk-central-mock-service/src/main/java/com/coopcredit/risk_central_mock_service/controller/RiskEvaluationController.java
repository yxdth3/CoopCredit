package com.coopcredit.risk_central_mock_service.controller;

import com.coopcredit.risk_central_mock_service.dto.RiskEvaluationRequestDTO;
import com.coopcredit.risk_central_mock_service.dto.RiskEvaluationResponseDTO;
import com.coopcredit.risk_central_mock_service.service.RiskEvaluationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/risk-evaluation")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Risk Evaluation", description = "Credit risk evaluation endpoints")
public class RiskEvaluationController {

        private final RiskEvaluationService riskEvaluationService;

        @Operation(summary = "Evaluate credit risk", description = "Evaluate credit risk based on document number. Returns consistent score for same document.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Risk evaluation completed successfully", content = @Content(schema = @Schema(implementation = RiskEvaluationResponseDTO.class))),
                        @ApiResponse(responseCode = "400", description = "Invalid input data")
        })
        @PostMapping
        public ResponseEntity<RiskEvaluationResponseDTO> evaluateRisk(
                        @Valid @RequestBody RiskEvaluationRequestDTO request) {

                log.info("Received risk evaluation request for document: {}", request.getDocument());

                RiskEvaluationResponseDTO response = riskEvaluationService.evaluateRisk(request);

                return ResponseEntity.ok(response);
        }

        @Operation(summary = "Evaluate with test scenarios", description = "Evaluate credit risk with predefined test scenarios. Use documents starting with 999, 888, 777, or 666 for specific scores.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Risk evaluation completed successfully", content = @Content(schema = @Schema(implementation = RiskEvaluationResponseDTO.class))),
                        @ApiResponse(responseCode = "400", description = "Invalid input data")
        })
        @PostMapping("/scenarios")
        public ResponseEntity<RiskEvaluationResponseDTO> evaluateRiskWithScenarios(
                        @Valid @RequestBody RiskEvaluationRequestDTO request) {

                log.info("Received risk evaluation request (with scenarios) for document: {}",
                                request.getDocument());

                RiskEvaluationResponseDTO response = riskEvaluationService.evaluateRiskWithScenarios(request);

                return ResponseEntity.ok(response);
        }

        @Operation(summary = "Health check", description = "Check if the service is running")
        @ApiResponse(responseCode = "200", description = "Service is healthy")
        @GetMapping("/health")
        public ResponseEntity<Map<String, String>> health() {
                Map<String, String> health = new HashMap<>();
                health.put("status", "UP");
                health.put("service", "Risk Central Mock Service");
                health.put("version", "1.0.0");

                return ResponseEntity.ok(health);
        }

        @Operation(summary = "Service information", description = "Get service information and available test scenarios")
        @ApiResponse(responseCode = "200", description = "Service information retrieved")
        @GetMapping("/info")
        public ResponseEntity<Map<String, Object>> info() {
                Map<String, Object> info = new HashMap<>();
                info.put("service", "Risk Central Mock Service");
                info.put("version", "1.0.0");
                info.put("description", "Mock service for credit risk evaluation");

                Map<String, String> testScenarios = new HashMap<>();
                testScenarios.put("999*", "Very High Risk (score: 800-1000)");
                testScenarios.put("888*", "High Risk (score: 600-799)");
                testScenarios.put("777*", "Medium Risk (score: 400-599)");
                testScenarios.put("666*", "Low Risk (score: 200-399)");
                testScenarios.put("other", "Random score based on document hash");

                info.put("testScenarios", testScenarios);

                return ResponseEntity.ok(info);
        }
}