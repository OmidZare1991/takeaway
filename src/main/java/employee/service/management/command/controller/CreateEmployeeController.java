package employee.service.management.command.controller;

import employee.service.management.command.dto.EmployeeRequest;
import employee.service.management.command.mappper.CreateEmployeeControllerMapper;
import employee.service.management.core.base_controller.BaseController;
import employee.service.management.core.domain.dto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.zalando.problem.Problem;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CreateEmployeeController extends BaseController {
    private final CommandGateway commandGateway;
    private final CreateEmployeeControllerMapper mapper;

    @PostMapping("employee")
    @Operation(summary = "New Employee Creation", description = "Create a new Employee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created", content =
                    {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = ResponseDto.class))}),
            @ApiResponse(responseCode = "422", description = "id cannot be null or empty"),
            @ApiResponse(responseCode = "400", description = "must be a well-formed email address"),
            @ApiResponse(responseCode = "400", description = "full name cannot be null or empty"),
            @ApiResponse(responseCode = "400", description = "birthdate cannot be null or empty"),
            @ApiResponse(responseCode = "403", description = "User not found"),
            @ApiResponse(responseCode = "417", description = "check your input and try again"),
            @ApiResponse(responseCode = "500", description = "Internal server error", content =
                    {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = Problem.class))})})
    public ResponseEntity<?> createEmployee(@RequestBody @Valid EmployeeRequest request) {
        commandGateway.send(mapper.toCreateEmployeeCommand(request));
        return new ResponseEntity<>(new ResponseDto<>("creating new employee with id: %s done successfully"), HttpStatus.CREATED);
    }
}
