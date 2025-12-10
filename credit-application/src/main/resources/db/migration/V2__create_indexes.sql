-- Indexes for better query performance

-- Affiliates indexes
CREATE INDEX idx_affiliates_document ON affiliates(document);
CREATE INDEX idx_affiliates_email ON affiliates(email);
CREATE INDEX idx_affiliates_status ON affiliates(status);

-- Credit applications indexes
CREATE INDEX idx_credit_applications_affiliate_id ON credit_applications(affiliate_id);
CREATE INDEX idx_credit_applications_status ON credit_applications(status);
CREATE INDEX idx_credit_applications_request_date ON credit_applications(request_date DESC);

-- Risk evaluations indexes
CREATE INDEX idx_risk_evaluations_document ON risk_evaluations(document);
CREATE INDEX idx_risk_evaluations_credit_application_id ON risk_evaluations(credit_application_id);

-- Users indexes
CREATE INDEX idx_users_username ON users(username);
CREATE INDEX idx_users_email ON users(email);