package com.coopcredit.credit_application.infrastructure.observability;


import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class MetricsService {

    private final Counter affiliateRegistrationCounter;
    private final Counter creditApplicationCounter;
    private final Counter creditApplicationApprovedCounter;
    private final Counter creditApplicationRejectedCounter;
    private final Counter authenticationFailureCounter;
    private final Counter riskEvaluationErrorCounter;
    private final Timer creditApplicationEvaluationTimer;

    public MetricsService(MeterRegistry meterRegistry) {
        // Counters
        this.affiliateRegistrationCounter = Counter.builder("affiliate.registration")
                .description("Number of affiliate registrations")
                .register(meterRegistry);

        this.creditApplicationCounter = Counter.builder("credit.application.created")
                .description("Number of credit applications created")
                .register(meterRegistry);

        this.creditApplicationApprovedCounter = Counter.builder("credit.application.approved")
                .description("Number of approved credit applications")
                .register(meterRegistry);

        this.creditApplicationRejectedCounter = Counter.builder("credit.application.rejected")
                .description("Number of rejected credit applications")
                .register(meterRegistry);

        this.authenticationFailureCounter = Counter.builder("authentication.failure")
                .description("Number of failed authentication attempts")
                .register(meterRegistry);

        this.riskEvaluationErrorCounter = Counter.builder("risk.evaluation.error")
                .description("Number of risk evaluation errors")
                .register(meterRegistry);

        // Timers
        this.creditApplicationEvaluationTimer = Timer.builder("credit.application.evaluation.time")
                .description("Time taken to evaluate credit applications")
                .register(meterRegistry);
    }

    public void incrementAffiliateRegistration() {
        affiliateRegistrationCounter.increment();
        log.debug("Affiliate registration metric incremented");
    }

    public void incrementCreditApplication() {
        creditApplicationCounter.increment();
        log.debug("Credit application metric incremented");
    }

    public void incrementCreditApplicationApproved() {
        creditApplicationApprovedCounter.increment();
        log.debug("Credit application approved metric incremented");
    }

    public void incrementCreditApplicationRejected() {
        creditApplicationRejectedCounter.increment();
        log.debug("Credit application rejected metric incremented");
    }

    public void incrementAuthenticationFailure() {
        authenticationFailureCounter.increment();
        log.debug("Authentication failure metric incremented");
    }

    public void incrementRiskEvaluationError() {
        riskEvaluationErrorCounter.increment();
        log.debug("Risk evaluation error metric incremented");
    }

    public void recordEvaluationTime(Runnable evaluation) {
        creditApplicationEvaluationTimer.record(evaluation);
    }

    public <T> T recordEvaluationTime(java.util.function.Supplier<T> evaluation) {
        return creditApplicationEvaluationTimer.record(evaluation);
    }
}