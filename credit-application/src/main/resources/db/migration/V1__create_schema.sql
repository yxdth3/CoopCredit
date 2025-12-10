-- Create affiliates table
CREATE TABLE affiliates (
                            id BIGSERIAL PRIMARY KEY,
                            first_name VARCHAR(100) NOT NULL,
                            last_name VARCHAR(100) NOT NULL,
                            document VARCHAR(20) NOT NULL UNIQUE,
                            email VARCHAR(150) NOT NULL UNIQUE,
                            phone VARCHAR(20),
                            salary DECIMAL(15, 2) NOT NULL CHECK (salary > 0),
                            affiliation_date DATE NOT NULL,
                            status VARCHAR(20) NOT NULL CHECK (status IN ('ACTIVE', 'INACTIVE', 'SUSPENDED')),
                            created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                            updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create credit_applications table
CREATE TABLE credit_applications (
                                     id BIGSERIAL PRIMARY KEY,
                                     affiliate_id BIGINT NOT NULL,
                                     requested_amount DECIMAL(15, 2) NOT NULL CHECK (requested_amount > 0),
                                     term_months INTEGER NOT NULL CHECK (term_months > 0 AND term_months <= 60),
                                     purpose VARCHAR(500),
                                     status VARCHAR(20) NOT NULL CHECK (status IN ('PENDING', 'APPROVED', 'REJECTED', 'CANCELLED')),
                                     request_date TIMESTAMP NOT NULL,
                                     evaluation_date TIMESTAMP,
                                     rejection_reason VARCHAR(500),
                                     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                     updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                     CONSTRAINT fk_affiliate FOREIGN KEY (affiliate_id) REFERENCES affiliates(id) ON DELETE CASCADE
);

-- Create risk_evaluations table
CREATE TABLE risk_evaluations (
                                  id BIGSERIAL PRIMARY KEY,
                                  credit_application_id BIGINT UNIQUE,
                                  document VARCHAR(20) NOT NULL,
                                  score INTEGER NOT NULL CHECK (score >= 0 AND score <= 1000),
                                  risk_level VARCHAR(20) NOT NULL CHECK (risk_level IN ('LOW', 'MEDIUM', 'HIGH')),
                                  evaluation_date TIMESTAMP NOT NULL,
                                  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                  CONSTRAINT fk_credit_application FOREIGN KEY (credit_application_id) REFERENCES credit_applications(id) ON DELETE CASCADE
);

-- Create users table for authentication
CREATE TABLE users (
                       id BIGSERIAL PRIMARY KEY,
                       username VARCHAR(50) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       email VARCHAR(150) NOT NULL UNIQUE,
                       enabled BOOLEAN NOT NULL DEFAULT TRUE,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create user_roles table
CREATE TABLE user_roles (
                            user_id BIGINT NOT NULL,
                            role VARCHAR(50) NOT NULL,
                            CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
                            PRIMARY KEY (user_id, role)
);