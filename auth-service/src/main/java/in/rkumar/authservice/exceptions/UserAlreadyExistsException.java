package in.rkumar.authservice.exceptions;

public class UserAlreadyExistsException extends RuntimeException {
	
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}