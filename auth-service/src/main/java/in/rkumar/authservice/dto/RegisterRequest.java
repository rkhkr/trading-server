package in.rkumar.authservice.dto;

import lombok.Data;

@Data
public class RegisterRequest {
	
    private String name;
    private String email;
    private String password;
    
    public RegisterRequest() { }
    
    public RegisterRequest(String name, String email, String password) {
    	this.name = name;
    	this.email = email;
    	this.password = password;
    }
    
    public void setName(String name) { this.name = name; }
    public String getName() { return name; }
    
    public void setEmail(String email) { this.email = email; }
    public String getEmail() { return email; }
    
    public void setPassword(String password) { this.password = password; }
    public String getPassword() { return password; }
}