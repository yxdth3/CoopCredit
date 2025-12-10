-- Insert default users
-- Password is 'admin123' encrypted with BCrypt
INSERT INTO users (username, password, email, enabled) VALUES
                                                           ('admin', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'admin@coopcredit.com', true),
                                                           ('analyst', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'analyst@coopcredit.com', true),
                                                           ('affiliate', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'affiliate@coopcredit.com', true);

-- Insert roles
INSERT INTO user_roles (user_id, role) VALUES
                                           (1, 'ROLE_ADMIN'),
                                           (1, 'ROLE_ANALISTA'),
                                           (2, 'ROLE_ANALISTA'),
                                           (3, 'ROLE_AFILIADO');

-- Insert sample affiliates
INSERT INTO affiliates (first_name, last_name, document, email, phone, salary, affiliation_date, status) VALUES
                                                                                                             ('Juan', 'Pérez', '1234567890', 'juan.perez@email.com', '3001234567', 3000000.00, '2023-01-15', 'ACTIVE'),
                                                                                                             ('María', 'García', '0987654321', 'maria.garcia@email.com', '3007654321', 4500000.00, '2023-03-20', 'ACTIVE'),
                                                                                                             ('Carlos', 'López', '1122334455', 'carlos.lopez@email.com', '3009876543', 2500000.00, '2024-06-10', 'ACTIVE'),
                                                                                                             ('Ana', 'Martínez', '5566778899', 'ana.martinez@email.com', '3005556666', 5000000.00, '2022-11-05', 'ACTIVE'),
                                                                                                             ('Luis', 'Rodríguez', '9988776655', 'luis.rodriguez@email.com', '3003334444', 2000000.00, '2024-11-01', 'INACTIVE');

-- Insert sample credit applications
INSERT INTO credit_applications (affiliate_id, requested_amount, term_months, purpose, status, request_date) VALUES
                                                                                                                 (1, 5000000.00, 12, 'Home improvement', 'PENDING', '2024-12-01 10:00:00'),
                                                                                                                 (2, 10000000.00, 24, 'Vehicle purchase', 'PENDING', '2024-12-05 14:30:00'),
                                                                                                                 (4, 8000000.00, 18, 'Debt consolidation', 'APPROVED', '2024-11-20 09:15:00');

-- Insert sample risk evaluation for approved application
INSERT INTO risk_evaluations (credit_application_id, document, score, risk_level, evaluation_date) VALUES
    (3, '5566778899', 750, 'LOW', '2024-11-20 10:00:00');

-- Update evaluation date for approved application
UPDATE credit_applications SET evaluation_date = '2024-11-20 10:00:00' WHERE id = 3;