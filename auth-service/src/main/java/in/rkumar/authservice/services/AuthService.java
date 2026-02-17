package in.rkumar.authservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.rkumar.authservice.dto.TokenValidationResponse;
import in.rkumar.authservice.exceptions.UserAlreadyExistsException;
import in.rkumar.authservice.models.UserCredential;
import in.rkumar.authservice.repositories.UserCredentialRepository;

@Service
public class AuthService {

    @Autowired
    private UserCredentialRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;
    
    private static String CLIENT_ID_PREFIX = "client";
    
    // Production Safe
    @Transactional // Ensures the ID generation and update happen atomically
    public String saveUser(String name, String email, String password) {
        
        // 1. Validation: Check if email exists
    	if (repository.findByEmail(email).isPresent())
            throw new UserAlreadyExistsException("Email already registered!");

        // 2. Create Object & Encrypt Password
        UserCredential credential = new UserCredential();
        credential.setName(name);
        credential.setEmail(email);
        credential.setPassword(passwordEncoder.encode(password));
        
        // 3. FIRST SAVE: Persist to DB to generate the Auto-Increment ID, The 'userId' is null at this point, which is fine temporarily.
        UserCredential savedUser = repository.save(credential);

        // 4. GENERATE ID: Now we have the unique DB ID (e.g., 5), We add 100 to start from "client101" instead of "client1"
        String customId = CLIENT_ID_PREFIX + (100 + savedUser.getId());

        // 5. UPDATE: Set the custom ID and Hibernate auto-updates it
        savedUser.setUserId(customId);
        
        // No need to call save() again! 
        // @Transactional detects the change and updates the DB automatically.
        
        return customId;
    }
    
//  public String saveUser(UserCredential credential) {
//	
//	if (repository.findByEmail(credential.getEmail()).isPresent())
//        throw new UserAlreadyExistsException("Email already registered!");
//
//    // 2. Generate a unique User ID (e.g., client + random 4 digits)
//    // In a real app, you might use a database sequence
//    String generatedUserId = "client" + (1000 + new Random().nextInt(9000));
//    credential.setUserId(generatedUserId);
//
//    // 3. Encrypt Password
//    credential.setPassword(passwordEncoder.encode(credential.getPassword()));
//
//    // 4. Save
//    repository.save(credential);
//    
//    return generatedUserId;
//  }

    public String generateToken(String userId) {
        return jwtService.generateToken(userId);
    }
    
    public TokenValidationResponse validateToken(String token) {
        try {
            // 1. Validate signature
            jwtService.validateToken(token); 
            
            // 2. Extract info if valid
            String userId = jwtService.extractUserId(token);
            String role = jwtService.extractRole(token); // return "USER" by default TODO: need to implement other roles
            
            return new TokenValidationResponse(true, userId, role);
            
        } catch (Exception e) {
            // Return "Invalid" instead of crashing
            return new TokenValidationResponse(false, null, null);
        }
    }
}