package com.coopcredit.risk_central_mock_service.controller;


import com.coopcredit.risk_central_mock_service.dto.RiskEvaluationRequestDTO;
import com.coopcredit.risk_central_mock_service.dto.RiskEvaluationResponseDTO;
import com.coopcredit.risk_central_mock_service.service.RiskEvaluationService;
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
public class RiskEvaluationController {

    private final RiskEvaluationService riskEvaluationService;

    /**
     * Main endpoint for risk evaluation
     * POST /api/risk-evaluation
     */
    @PostMapping
    public ResponseEntity<RiskEvaluationResponseDTO> evaluateRisk(
            @Valid @RequestBody RiskEvaluationRequestDTO request) {

        log.info("Received risk evaluation request for document: {}", request.getDocument());

        RiskEvaluationResponseDTO response = riskEvaluationService.evaluateRisk(request);

        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint with scenario-based evaluation (useful for testing)
     * POST /api/risk-evaluation/scenarios
     */
    @PostMapping("/scenarios")
    public ResponseEntity<RiskEvaluationResponseDTO> evaluateRiskWithScenarios(
            @Valid @RequestBody RiskEvaluationRequestDTO request) {

        log.info("Received risk evaluation request (with scenarios) for document: {}",
                request.getDocument());

        RiskEvaluationResponseDTO response =
                riskEvaluationService.evaluateRiskWithScenarios(request);

        return ResponseEntity.ok(response);
    }

    /**
     * Health check endpoint
     * GET /api/risk-evaluation/health
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        Map<String, String> health = new HashMap<>();
        health.put("status", "UP");
        health.put("service", "Risk Central Mock Service");
        health.put("version", "1.0.0");

        return ResponseEntity.ok(health);
    }

    /**
     * Info endpoint with testing scenarios
     * GET /api/risk-evaluation/info
     */
    @GetMapping("/info")
    public ResponseEntity<Map<String, Object>> info() {
        Map<String, Object> info = new HashMap<>();
        info.put("service", "Risk Central Mock Service");
        info.put("description", "Mock service for credit risk evaluation");
        info.put("version", "1.0.0");

        Map<String, String> scenarios = new HashMap<>();
        scenarios.put("999*", "Excellent credit (score ~800)");
        scenarios.put("888*", "Good credit (score ~700)");
        scenarios.put("777*", "Poor credit (score ~500)");
        scenarios.put("666*", "Very poor credit (score ~350)");
        scenarios.put("others", "Random score based on document hash");

        info.put("testingScenarios", scenarios);
        info.put("endpoint", "POST /api/risk-evaluation");
        info.put("scenariosEndpoint", "POST /api/risk-evaluation/scenarios");

        return ResponseEntity.ok(info);
    }
}