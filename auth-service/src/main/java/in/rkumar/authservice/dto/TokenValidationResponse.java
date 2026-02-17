package in.rkumar.authservice.dto;

import lombok.Data;

@Data
public class TokenValidationResponse {
	
    private boolean isValid;
    private String userId;
    private String role;
    
    public TokenValidationResponse() { }
    
    public TokenValidationResponse(boolean isValid, String userId, String role) {
    	this.isValid = isValid;
    	this.userId = userId;
    	this.role = role;
    }
    
    
    public void setIsValid(boolean isValid) { this.isValid = isValid; }
    public boolean getIsValid() { return isValid; }
    
    public void setUserId(String userId) { this.userId = userId; }
    public String getUserId() { return userId; }
    
    public void setRole(String role) { this.role = role; }
    public String getRole() { return role; }
}