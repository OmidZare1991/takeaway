package employee.service.management.command.controller;

import employee.service.management.command.dto.EmployeeRequest;
import employee.service.management.command.mappper.UpdateEmployeeControllerMapper;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.zalando.problem.Problem;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UpdateEmployeeController extends BaseController {
    private final CommandGateway commandGateway;
    private final UpdateEmployeeControllerMapper mapper;

    @PutMapping("employee/{id}")
    @Operation(summary = "Employee Update", description = "Update the existing Employee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content =
                    {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = ResponseDto.class))}),
            @ApiResponse(responseCode = "400", description = "must be a well-formed email address"),
            @ApiResponse(responseCode = "400", description = "full name cannot be null or empty"),
            @ApiResponse(responseCode = "400", description = "birthdate cannot be null or empty"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "forbidden"),
            @ApiResponse(responseCode = "500", description = "Internal server error")})
    public ResponseEntity<?> createEmployee(@RequestBody @Valid EmployeeRequest request, @PathVariable String id) {
        commandGateway.sendAndWait(mapper.toUpdateEmployeeCommand(request, id));
        return ResponseEntity.ok(new ResponseDto<>("updating the employee done successfully"));
    }
}
