package com.coopcredit.credit_application.infrastructure.adapter.out.persistence.entity;


import com.coopcredit.credit_application.domain.model.RiskEvaluationStatus;
import jakarta.persistence.*;
        import lombok.*;
        import java.time.LocalDateTime;

@Entity
@Table(name = "risk_evaluations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RiskEvaluationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "credit_application_id", unique = true)
    private CreditApplicationEntity creditApplication;

    @Column(nullable = false, length = 20)
    private String document;

    @Column(nullable = false)
    private Integer score;

    @Enumerated(EnumType.STRING)
    @Column(name = "risk_level", nullable = false, length = 20)
    private RiskEvaluationStatus riskEvaluationStatus;

    @Column(name = "evaluation_date", nullable = false)
    private LocalDateTime evaluationDate;
}