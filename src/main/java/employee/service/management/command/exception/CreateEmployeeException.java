package employee.service.management.command.exception;

public class CreateEmployeeException extends RuntimeException{
    public CreateEmployeeException(String message) {
        super(message);
    }

    public CreateEmployeeException(String message, Throwable cause) {
        super(message, cause);
    }
}
