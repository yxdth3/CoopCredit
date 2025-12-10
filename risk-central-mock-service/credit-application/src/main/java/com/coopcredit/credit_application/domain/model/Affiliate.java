package com.coopcredit.credit_application.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Affiliate {

    private Long id;
    private String firstName;
    private String lastName;
    private String document;
    private String email;
    private String phone;
    private Double salary;
    private LocalDate affiliationDate;
    private AffiliateStatus status;

    // Business logic methods
    public boolean isActive() {
        return this.status == AffiliateStatus.ACTIVE;
    }

    public boolean canRequestCredit() {
        return isActive() && salary != null && salary > 0;
    }

    public Double getMaxCreditAmount() {
        if (salary == null) return 0.0;
        return salary * 3; // Max 3 times salary
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public boolean hasMinimumSeniority(int months) {
        if (affiliationDate == null) return false;
        LocalDate minimumDate = LocalDate.now().minusMonths(months);
        return affiliationDate.isBefore(minimumDate) || affiliationDate.isEqual(minimumDate);
    }
}