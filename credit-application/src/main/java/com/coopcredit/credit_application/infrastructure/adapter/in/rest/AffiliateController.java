package com.coopcredit.credit_application.infrastructure.adapter.in.rest;



import com.coopcredit.credit_application.application.dto.request.AffiliateRequestDTO;
import com.coopcredit.credit_application.application.dto.response.AffiliateResponseDTO;
import com.coopcredit.credit_application.application.mapper.AffiliateMapper;
import com.coopcredit.credit_application.domain.model.Affiliate;
import com.coopcredit.credit_application.domain.port.in.GetAffiliateUseCase;
import com.coopcredit.credit_application.domain.port.in.RegisterAffiliateUseCase;
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
@RequestMapping("/api/affiliates")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Affiliates", description = "Affiliate management endpoints")
@SecurityRequirement(name = "bearerAuth")
public class AffiliateController {

    private final RegisterAffiliateUseCase registerAffiliateUseCase;
    private final GetAffiliateUseCase getAffiliateUseCase;
    private final AffiliateMapper mapper;
    private final MetricsService metricsService;

    @Operation(
            summary = "Register new affiliate",
            description = "Register a new affiliate in the cooperative. Requires ADMIN or ANALISTA role."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Affiliate registered successfully",
                    content = @Content(schema = @Schema(implementation = AffiliateResponseDTO.class))
            ),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden - insufficient permissions"),
            @ApiResponse(responseCode = "409", description = "Duplicate document")
    })
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ANALISTA')")
    public ResponseEntity<AffiliateResponseDTO> registerAffiliate(
            @Valid @RequestBody AffiliateRequestDTO request) {
        log.info("Registering new affiliate with document: {}", request.getDocument());

        Affiliate affiliate = mapper.toDomain(request);
        Affiliate savedAffiliate = registerAffiliateUseCase.execute(affiliate);
        AffiliateResponseDTO response = mapper.toResponseDTO(savedAffiliate);

        metricsService.incrementAffiliateRegistration();

        log.info("Affiliate registered successfully with id: {}", response.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            summary = "Get affiliate by ID",
            description = "Retrieve affiliate information by ID"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Affiliate found",
                    content = @Content(schema = @Schema(implementation = AffiliateResponseDTO.class))
            ),
            @ApiResponse(responseCode = "404", description = "Affiliate not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ANALISTA', 'AFILIADO')")
    public ResponseEntity<AffiliateResponseDTO> getAffiliateById(
            @Parameter(description = "Affiliate ID", required = true)
            @PathVariable Long id) {
        log.info("Getting affiliate by id: {}", id);

        Affiliate affiliate = getAffiliateUseCase.getById(id);
        AffiliateResponseDTO response = mapper.toResponseDTO(affiliate);

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Get affiliate by document",
            description = "Retrieve affiliate information by document number. Requires ADMIN or ANALISTA role."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Affiliate found",
                    content = @Content(schema = @Schema(implementation = AffiliateResponseDTO.class))
            ),
            @ApiResponse(responseCode = "404", description = "Affiliate not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @GetMapping("/document/{document}")
    @PreAuthorize("hasAnyRole('ADMIN', 'ANALISTA')")
    public ResponseEntity<AffiliateResponseDTO> getAffiliateByDocument(
            @Parameter(description = "Document number", required = true)
            @PathVariable String document) {
        log.info("Getting affiliate by document: {}", document);

        Affiliate affiliate = getAffiliateUseCase.getByDocument(document);
        AffiliateResponseDTO response = mapper.toResponseDTO(affiliate);

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Get all affiliates",
            description = "Retrieve all registered affiliates. Requires ADMIN or ANALISTA role."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "List of affiliates retrieved successfully"
            ),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ANALISTA')")
    public ResponseEntity<List<AffiliateResponseDTO>> getAllAffiliates() {
        log.info("Getting all affiliates");

        List<Affiliate> affiliates = getAffiliateUseCase.getAll();
        List<AffiliateResponseDTO> response = mapper.toResponseDTOList(affiliates);

        return ResponseEntity.ok(response);
    }
}