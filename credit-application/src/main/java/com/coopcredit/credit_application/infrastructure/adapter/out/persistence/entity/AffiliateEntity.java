package com.coopcredit.credit_application.infrastructure.adapter.out.persistence.entity;

import com.coopcredit.credit_application.domain.model.AffiliateStatus;
import jakarta.persistence.*;
        import lombok.*;

        import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "affiliates")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AffiliateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    @Column(nullable = false, unique = true, length = 20)
    private String document;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @Column(length = 20)
    private String phone;

    @Column(nullable = false)
    private Double salary;

    @Column(name = "affiliation_date", nullable = false)
    private LocalDate affiliationDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private AffiliateStatus status;

    @OneToMany(mappedBy = "affiliate", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<CreditApplicationEntity> creditApplications = new ArrayList<>();
}