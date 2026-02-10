package in.rkumar.gateway.filters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import in.rkumar.gateway.services.JwtService;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    @Autowired
    private RouteValidator validator;

    @Autowired
    private JwtService jwtService;

    public AuthenticationFilter() {
        super(Config.class);
    }

    public static class Config { }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            // 1. Check if the route is secured
            if (validator.isSecured.test(exchange.getRequest())) {
                // 2. Check if Authorization header is present
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new RuntimeException("Missing Authorization Header");
                }

                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);
                }

                try {
                    // 3. Validate Token
                    jwtService.validateToken(authHeader);
                    
                    // 4. Extract Username from Token
                    String username = jwtService.extractUsername(authHeader);

                    // 5. Add "loggedInUser" Header to the Request, We explicitly mutate (change) the request to include this new header
                    ServerHttpRequest request = exchange.getRequest()
                            .mutate()
                            .header("loggedInUser", username)
                            .build();
                    
                    return chain.filter(exchange.mutate().request(request).build());
                    
                } catch (Exception e) {
                    System.out.println("Invalid Access...!");
                    throw new RuntimeException("Unauthorized access");
                }
            }
            return chain.filter(exchange);
        });
    }
}