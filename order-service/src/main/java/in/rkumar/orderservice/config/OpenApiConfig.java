package in.rkumar.orderservice.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@Configuration
@OpenAPIDefinition(
    info = @Info(title = "Order Service API", version = "1.0"),
    servers = {
        @Server(url = "http://localhost:8080", description = "API Gateway URL"),
        @Server(url = "http://localhost:8081", description = "Direct Service URL")
    },
    // Tell Swagger this API is secured globally
    security = @SecurityRequirement(name = "bearerAuth") 
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
