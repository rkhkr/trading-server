package in.rkumar.authservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @Info(title = "Auth Service API", version = "1.0", description = "Register and Login Users"),
    servers = {
        @Server(url = "http://localhost:8080", description = "API Gateway URL"), // ⚠️ Important for routing
        @Server(url = "http://localhost:9898", description = "Direct Service URL")
    },
    security = @SecurityRequirement(name = "bearerAuth") // Tell Swagger this API is secured globally
)
// Define what "bearerAuth" means (JWT)
@SecurityScheme(
    name = "bearerAuth",
    type = SecuritySchemeType.HTTP,
    scheme = "bearer",
    bearerFormat = "JWT"
)
public class OpenApiConfig {
    // No code needed inside! The annotations do all the work.
}