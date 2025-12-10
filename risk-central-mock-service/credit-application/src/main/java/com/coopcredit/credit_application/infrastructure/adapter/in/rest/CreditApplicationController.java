package com.coopcredit.credit_application.infrastructure.adapter.in.rest;


import com.coopcredit.credit_application.application.dto.request.CreditApplicationRequestDTO;
import com.coopcredit.credit_application.application.dto.response.CreditApplicationResponseDTO;
import com.coopcredit.credit_application.application.mapper.CreditApplicationMapper;
import com.coopcredit.credit_application.domain.model.Affiliate;
import com.coopcredit.credit_application.domain.model.CreditApplication;
import com.coopcredit.credit_application.domain.port.in.EvaluateCreditApplicationUseCase;
import com.coopcredit.credit_application.domain.port.in.GetAffiliateUseCase;
import com.coopcredit.credit_application.domain.port.in.GetCreditApplicationUseCase;
import com.coopcredit.credit_application.domain.port.in.RegisterCreditApplicationUseCase;
import com.coopcredit.credit_application.infrastructure.observability.MetricsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/credit-applications")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Credit Applications", description = "Credit application management and evaluation endpoints")
@SecurityRequirement(name = "bearerAuth")
public class CreditApplicationController {

    private final RegisterCreditApplicationUseCase registerCreditApplicationUseCase;
    private final GetCreditApplicationUseCase getCreditApplicationUseCase;
    private final EvaluateCreditApplicationUseCase evaluateCreditApplicationUseCase;
    private final GetAffiliateUseCase getAffiliateUseCase;
    private final CreditApplicationMapper mapper;
    private final MetricsService metricsService;

    @Operation(
            summary = "Create credit application",
            description = "Submit a new credit application. Requires ADMIN or AFILIADO role."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Credit application created successfully",
                    content = @Content(schema = @Schema(implementation = CreditApplicationResponseDTO.class))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid input data or business rule violation"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden - Affiliate is inactive"),
            @ApiResponse(responseCode = "404", description = "Affiliate not found")
    })
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'AFILIADO')")
    public ResponseEntity<CreditApplicationResponseDTO> registerApplication(
            @Valid @RequestBody CreditApplicationRequestDTO request) {
        log.info("Registering new credit application for affiliate id: {}", request.getAffiliateId());

        CreditApplication application = mapper.toDomain(request);
        CreditApplication savedApplication = registerCreditApplicationUseCase.execute(application);

        CreditApplicationResponseDTO response = mapper.toResponseDTO(savedApplication);
        enrichResponseWithAffiliateName(response, savedApplication.getAffiliateId());

        metricsService.incrementCreditApplication();

        log.info("Credit application registered successfully with id: {}", response.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            summary = "Evaluate credit application",
            description = "Evaluate a pending credit application using external risk service. Requires ADMIN or ANALISTA role."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Credit application evaluated successfully",
                    content = @Content(schema = @Schema(implementation = CreditApplicationResponseDTO.class))
            ),
            @ApiResponse(responseCode = "400", description = "Application already evaluated or invalid state"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Application not found"),
            @ApiResponse(responseCode = "503", description = "Risk evaluation service unavailable")
    })
    @PostMapping("/{id}/evaluate")
    @PreAuthorize("hasAnyRole('ADMIN', 'ANALISTA')")
    public ResponseEntity<CreditApplicationResponseDTO> evaluateApplication(
            @Parameter(description = "Credit application ID", required = true)
            @PathVariable Long id) {
        log.info("Evaluating credit application with id: {}", id);

        CreditApplication evaluatedApplication = metricsService.recordEvaluationTime(
                () -> evaluateCreditApplicationUseCase.execute(id)
        );

        CreditApplicationResponseDTO response = mapper.toResponseDTO(evaluatedApplication);
        enrichResponseWithAffiliateName(response, evaluatedApplication.getAffiliateId());

        if (evaluatedApplication.isApproved()) {
            metricsService.incrementCreditApplicationApproved();
        } else {
            metricsService.incrementCreditApplicationRejected();
        }

        log.info("Credit application evaluated successfully. Status: {}", response.getStatus());
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Get credit application by ID",
            description = "Retrieve credit application details by ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Credit application found",
                    content = @Content(schema = @Schema(implementation = CreditApplicationResponseDTO.class))
            ),
            @ApiResponse(responseCode = "404", description = "Application not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ANALISTA', 'AFILIADO')")
    public ResponseEntity<CreditApplicationResponseDTO> getApplicationById(
            @Parameter(description = "Credit application ID", required = true)
            @PathVariable Long id) {
        log.info("Getting credit application by id: {}", id);

        CreditApplication application = getCreditApplicationUseCase.getById(id);

        CreditApplicationResponseDTO response = mapper.toResponseDTO(application);
        enrichResponseWithAffiliateName(response, application.getAffiliateId());

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Get applications by affiliate",
            description = "Retrieve all credit applications for a specific affiliate"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "List of applications retrieved successfully"
            ),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping("/affiliate/{affiliateId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ANALISTA', 'AFILIADO')")
    public ResponseEntity<List<CreditApplicationResponseDTO>> getApplicationsByAffiliate(
            @Parameter(description = "Affiliate ID", required = true)
            @PathVariable Long affiliateId) {
        log.info("Getting credit applications for affiliate id: {}", affiliateId);

        List<CreditApplication> applications = getCreditApplicationUseCase.getByAffiliateId(affiliateId);
        List<CreditApplicationResponseDTO> response = mapper.toResponseDTOList(applications);

        response.forEach(dto -> enrichResponseWithAffiliateName(dto, affiliateId));

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Get pending applications",
            description = "Retrieve all pending credit applications. Requires ADMIN or ANALISTA role."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "List of pending applications retrieved successfully"
            ),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @GetMapping("/pending")
    @PreAuthorize("hasAnyRole('ADMIN', 'ANALISTA')")
    public ResponseEntity<List<CreditApplicationResponseDTO>> getPendingApplications() {
        log.info("Getting all pending credit applications");

        List<CreditApplication> applications = getCreditApplicationUseCase.getAllPending();
        List<CreditApplicationResponseDTO> response = mapper.toResponseDTOList(applications);

        response.forEach(dto -> enrichResponseWithAffiliateName(dto, dto.getAffiliateId()));

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Get all applications",
            description = "Retrieve all credit applications. Requires ADMIN or ANALISTA role."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "List of applications retrieved successfully"
            ),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ANALISTA')")
    public ResponseEntity<List<CreditApplicationResponseDTO>> getAllApplications() {
        log.info("Getting all credit applications");

        List<CreditApplication> applications = getCreditApplicationUseCase.getAll();
        List<CreditApplicationResponseDTO> response = mapper.toResponseDTOList(applications);

        response.forEach(dto -> enrichResponseWithAffiliateName(dto, dto.getAffiliateId()));

        return ResponseEntity.ok(response);
    }

    private void enrichResponseWithAffiliateName(CreditApplicationResponseDTO response, Long affiliateId) {
        try {
            Affiliate affiliate = getAffiliateUseCase.getById(affiliateId);
            response.setAffiliateName(affiliate.getFullName());
        } catch (Exception e) {
            log.warn("Could not fetch affiliate name for id: {}", affiliateId);
            response.setAffiliateName("Unknown");
        }
    }
}