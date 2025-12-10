package com.coopcredit.credit_application.infrastructure.adapter.out.persistance.entity;


import com.coopcredit.credit_application.domain.model.CreditApplicationStatus;
import jakarta.persistence.*;
        import lombok.*;
        import java.time.LocalDateTime;

@Entity
@Table(name = "credit_applications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreditApplicationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "affiliate_id", nullable = false)
    private AffiliateEntity affiliate;

    @Column(name = "requested_amount", nullable = false)
    private Double requestedAmount;

    @Column(name = "term_months", nullable = false)
    private Integer termMonths;

    @Column(length = 500)
    private String purpose;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private CreditApplicationStatus status;

    @Column(name = "request_date", nullable = false)
    private LocalDateTime requestDate;

    @Column(name = "evaluation_date")
    private LocalDateTime evaluationDate;

    @Column(name = "rejection_reason", length = 500)
    private String rejectionReason;

    @OneToOne(mappedBy = "creditApplication", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private RiskEvaluationEntity riskEvaluation;
}