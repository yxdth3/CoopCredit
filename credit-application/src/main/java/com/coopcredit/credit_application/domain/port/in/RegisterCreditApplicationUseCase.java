package com.coopcredit.credit_application.domain.port.in;

import com.coopcredit.credit_application.domain.model.CreditApplication;

public interface RegisterCreditApplicationUseCase {
    CreditApplication execute(CreditApplication application);
}
