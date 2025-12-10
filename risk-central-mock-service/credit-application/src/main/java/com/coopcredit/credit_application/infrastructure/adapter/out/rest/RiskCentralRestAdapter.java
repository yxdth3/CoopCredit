package com.coopcredit.credit_application.infrastructure.adapter.out.rest;


import com.coopcredit.credit_application.domain.exception.RiskEvaluationException;
import com.coopcredit.credit_application.domain.model.RiskEvaluation;
import com.coopcredit.credit_application.domain.port.out.RiskEvaluationServicePort;
import com.coopcredit.credit_application.infrastructure.adapter.out.rest.dto.RiskRequestDTO;
import com.coopcredit.credit_application.infrastructure.adapter.out.rest.dto.RiskResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
        import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@Component
@Slf4j
public class RiskCentralRestAdapter implements RiskEvaluationServicePort {

    private final RestTemplate restTemplate;
    private final String riskCentralUrl;

    public RiskCentralRestAdapter(
            RestTemplate restTemplate,
            @Value("${risk-central.url}") String riskCentralUrl) {
        this.restTemplate = restTemplate;
        this.riskCentralUrl = riskCentralUrl;
    }

    @Override
    public RiskEvaluation evaluateRisk(String document) {
        log.info("Calling Risk Central service for document: {}", document);

        try {
            String url = riskCentralUrl + "/api/risk-evaluation";

            RiskRequestDTO request = RiskRequestDTO.builder()
                    .document(document)
                    .build();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<RiskRequestDTO> entity = new HttpEntity<>(request, headers);

            ResponseEntity<RiskResponseDTO> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    entity,
                    RiskResponseDTO.class
            );

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                RiskResponseDTO responseBody = response.getBody();

                log.info("Risk evaluation received - Document: {}, Score: {}, Risk: {}",
                        responseBody.getDocument(),
                        responseBody.getScore(),
                        responseBody.getRiskLevel());

                return RiskEvaluation.builder()
                        .document(responseBody.getDocument())
                        .score(responseBody.getScore())
                        .riskLevel(RiskLevel.valueOf(responseBody.getRiskLevel()))
                        .evaluationDate(LocalDateTime.now())
                        .build();
            } else {
                throw new RiskEvaluationException("Invalid response from Risk Central service");
            }

        } catch (HttpClientErrorException e) {
            log.error("HTTP error calling Risk Central: {} - {}", e.getStatusCode(), e.getMessage());
            throw new RiskEvaluationException("Error calling Risk Central service: " + e.getMessage(), e);

        } catch (ResourceAccessException e) {
            log.error("Connection error to Risk Central: {}", e.getMessage());
            throw new RiskEvaluationException("Risk Central service is unavailable", e);

        } catch (Exception e) {
            log.error("Unexpected error calling Risk Central: {}", e.getMessage(), e);
            throw new RiskEvaluationException("Unexpected error evaluating risk", e);
        }
    }
}