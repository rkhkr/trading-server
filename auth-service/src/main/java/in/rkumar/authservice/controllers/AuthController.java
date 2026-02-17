package in.rkumar.authservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication; // ⚠️ Not tomcat.Authentication
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import in.rkumar.authservice.dto.AuthRequest;
import in.rkumar.authservice.dto.RegisterRequest;
import in.rkumar.authservice.dto.TokenValidationResponse;
import in.rkumar.authservice.services.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {
    
    @Autowired
    private AuthService service;

    @Autowired
    private AuthenticationManager authenticationManager;
    
//    @PostMapping("/register")
//    public String addNewUser(@RequestBody UserCredential user) {
//        final String newUserId = service.saveUser(user);
//        return "User registered successfully! Your User ID is: " + newUserId;
//    }
    
    @PostMapping("/register")
    public String addNewUser(@RequestBody RegisterRequest request) {
    	return service.saveUser(request.getName(), request.getEmail(), request.getPassword());
    }

    @PostMapping("/token")
    public String getToken(@RequestBody AuthRequest authRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUserId(), authRequest.getPassword())        );
        return authenticate.isAuthenticated() ? service.generateToken(authRequest.getUserId()) : "Invalid Access";
    }

    @GetMapping("/validate")
    public ResponseEntity<TokenValidationResponse> validateToken(@RequestParam("token") String token) {
        return ResponseEntity.ok(service.validateToken(token));
    }
}