package employee.service.management.core.security.exception;

public class UserAlreadyFoundException extends RuntimeException{
    public UserAlreadyFoundException(String message) {
        super(message);
    }
}
