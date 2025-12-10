package com.coopcredit.risk_central_mock_service.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Risk Central Mock Service API",
                version = "1.0",
                description = "Mock service for credit risk evaluation",
                contact = @Contact(
                        name = "Risk Central Team",
                        email = "risk@coopcredit.com"
                ),
                license = @License(
                        name = "MIT License",
                        url = "https://opensource.org/licenses/MIT"
                )
        ),
        servers = {
                @Server(
                        description = "Local Development",
                        url = "http://localhost:8081"
                ),
                @Server(
                        description = "Production",
                        url = "https://risk.coopcredit.com"
                )
        }
)
public class OpenApiConfig {
}