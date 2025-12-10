package com.coopcredit.credit_application.application.dto.response;


import com.coopcredit.credit_application.domain.model.AffiliateStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AffiliateResponseDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String fullName;
    private String document;
    private String email;
    private String phone;
    private Double salary;
    private LocalDate affiliationDate;
    private AffiliateStatus status;
    private Double maxCreditAmount;
    private Boolean canRequestCredit;
}