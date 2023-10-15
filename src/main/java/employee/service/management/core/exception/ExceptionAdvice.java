package employee.service.management.core.exception;

import employee.service.management.command.exception.CreateEmployeeException;
import employee.service.management.command.exception.DeleteEmployeeException;
import employee.service.management.core.security.exception.UserAlreadyFoundException;
import org.axonframework.commandhandling.CommandExecutionException;
import org.axonframework.eventsourcing.AggregateDeletedException;
import org.axonframework.queryhandling.QueryExecutionException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;
import org.zalando.problem.spring.web.advice.ProblemHandling;
import org.zalando.problem.spring.web.advice.security.SecurityAdviceTrait;

import java.util.List;
import java.util.concurrent.CompletionException;

import static employee.service.management.core.exception.ErrorConstants.MESSAGE_KEY;

@RestControllerAdvice
public class ExceptionAdvice implements ProblemHandling, SecurityAdviceTrait {



    @Override
    public ResponseEntity<Problem> handleAccessDenied(AccessDeniedException e, NativeWebRequest request) {
        return SecurityAdviceTrait.super.handleAccessDenied(e, request);
    }

    @Override
    public ResponseEntity<Problem> handleAuthentication(AuthenticationException e, NativeWebRequest request) {
        return SecurityAdviceTrait.super.handleAuthentication(e, request);
    }

    @Override
    public ResponseEntity<Problem> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, NativeWebRequest request) {
        List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();
        String errorMessage = allErrors.get(0).getDefaultMessage();
        Problem problem = Problem.builder().withStatus(Status.BAD_REQUEST).with(MESSAGE_KEY, errorMessage).build();
        return create(ex, problem, request);
    }

    @Override
    public ResponseEntity<Problem> handleRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException exception, NativeWebRequest request) {
        Problem problem = Problem.builder().withStatus(Status.METHOD_NOT_ALLOWED).with(MESSAGE_KEY, exception.getMessage()).build();

        return create(exception, problem, request);
    }

    @Override
    public ResponseEntity<Problem> handleUnsupportedOperation(UnsupportedOperationException exception, NativeWebRequest request) {
        Problem problem = Problem.builder().withStatus(Status.METHOD_NOT_ALLOWED).with(MESSAGE_KEY, exception.getMessage()).build();
        return create(exception, problem, request);
    }

    @Override
    public ResponseEntity<Problem> handleMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException exception, NativeWebRequest request) {
        Problem problem = Problem.builder().withStatus(Status.NOT_ACCEPTABLE).with(MESSAGE_KEY, exception.getMessage()).build();
        return create(exception, problem, request);
    }

    @Override
    public ResponseEntity<Problem> handleMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException exception, NativeWebRequest request) {
        Problem problem = Problem.builder().withStatus(Status.UNSUPPORTED_MEDIA_TYPE).with(MESSAGE_KEY, exception.getMessage()).build();
        return create(exception, problem, request);
    }

    @ExceptionHandler
    public ResponseEntity<Problem> handleException(Exception ex, NativeWebRequest request) {
        Problem problem = Problem.builder().withStatus(Status.INTERNAL_SERVER_ERROR).with(MESSAGE_KEY, ex.getMessage()).build();
        return create(ex, problem, request);
    }

    @ExceptionHandler
    public ResponseEntity<Problem> handleDeleteEmployeeException(DeleteEmployeeException ex, NativeWebRequest request) {
        Problem problem = Problem.builder().withStatus(Status.UNPROCESSABLE_ENTITY).with(MESSAGE_KEY, ex.getMessage()).build();
        return create(ex, problem, request);
    }

    @ExceptionHandler
    public ResponseEntity<Problem> handleCreateEmployeeException(CreateEmployeeException ex, NativeWebRequest request) {
        Problem problem = Problem.builder().withStatus(Status.UNPROCESSABLE_ENTITY).with(MESSAGE_KEY, ex.getMessage()).build();
        return create(ex, problem, request);
    }

    @ExceptionHandler
    public ResponseEntity<Problem> handleQueryExecutionException(QueryExecutionException ex, NativeWebRequest request) {
        Problem problem = Problem.builder().withStatus(Status.EXPECTATION_FAILED).with(MESSAGE_KEY, "check your input and try again").build();
        return create(ex, problem, request);
    }

    @ExceptionHandler
    public ResponseEntity<Problem> handleCommandExecutionException(CommandExecutionException ex, NativeWebRequest request) {
        Problem problem = Problem.builder().withStatus(Status.EXPECTATION_FAILED).with(MESSAGE_KEY, "check your input and try again").build();
        return create(ex, problem, request);
    }
    @ExceptionHandler
    public ResponseEntity<Problem> handleCompletionException(CompletionException ex, NativeWebRequest request) {
        Problem problem = Problem.builder().withStatus(Status.EXPECTATION_FAILED).with(MESSAGE_KEY, "check your input and try again").build();
        return create(ex, problem, request);
    }

    @ExceptionHandler
    public ResponseEntity<Problem> handleUserAlreadyFoundException(UserAlreadyFoundException ex, NativeWebRequest request) {
        Problem problem = Problem.builder().withStatus(Status.BAD_REQUEST).with(MESSAGE_KEY, ex.getMessage()).build();
        return create(ex, problem, request);
    }

    @ExceptionHandler
    public ResponseEntity<Problem> handleUsernameNotFoundException(UsernameNotFoundException ex, NativeWebRequest request) {
        Problem problem = Problem.builder().withStatus(Status.FORBIDDEN).with(MESSAGE_KEY, ex.getMessage()).build();
        return create(ex, problem, request);
    }

    @ExceptionHandler
    public ResponseEntity<Problem> handleAggregateDeletedException(AggregateDeletedException ex, NativeWebRequest request) {
        Problem problem = Problem.builder().withStatus(Status.FORBIDDEN).with(MESSAGE_KEY, "employee with this id is not found").build();
        return create(ex, problem, request);
    }
    @ExceptionHandler
    public ResponseEntity<Problem> handleServerError(HttpServerErrorException ex, NativeWebRequest request) {
        Problem problem = Problem.builder().withStatus(Status.INTERNAL_SERVER_ERROR).with(MESSAGE_KEY, ex.getMessage()).build();
        return create(ex, problem, request);
    }

    @ExceptionHandler
    public ResponseEntity<Problem> handleIllegalArgumentException(IllegalArgumentException ex, NativeWebRequest request) {
        Problem problem = Problem.builder().withStatus(Status.BAD_REQUEST).with(MESSAGE_KEY, ex.getMessage()).build();
        return create(ex, problem, request);
    }

    @Override
    public ResponseEntity<Problem> handleNoHandlerFound(NoHandlerFoundException ex, NativeWebRequest request) {
        Problem problem = Problem.builder().withStatus(Status.NOT_FOUND).with(MESSAGE_KEY, ex.getMessage()).build();
        return create(ex, problem, request);
    }

    @Override
    public boolean isCausalChainsEnabled() {
        return false;
    }

}
