package employee.service.management.command.exception;

public class DeleteEmployeeException extends RuntimeException{
    public DeleteEmployeeException(String message) {
        super(message);
    }
}
