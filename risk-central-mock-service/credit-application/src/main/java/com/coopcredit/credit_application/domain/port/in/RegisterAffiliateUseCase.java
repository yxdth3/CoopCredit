package com.coopcredit.credit_application.domain.port.in;

import com.coopcredit.credit_application.domain.model.Affiliate;

public interface RegisterAffiliateUseCase {
    Affiliate execute(Affiliate affiliate);
}
