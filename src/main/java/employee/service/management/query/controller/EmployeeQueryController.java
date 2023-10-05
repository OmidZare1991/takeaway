package employee.service.management.query.controller;


import employee.service.management.core.base_controller.BaseController;
import employee.service.management.core.domain.dto.ResponseDto;
import employee.service.management.query.dto.*;
import employee.service.management.query.mapper.EmployeeEventControllerMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.zalando.problem.Problem;

@RestController
@RequiredArgsConstructor
public class EmployeeQueryController extends BaseController {

    private final QueryGateway queryGateway;
    private final EmployeeEventControllerMapper mapper;

    @GetMapping("employees")
    @Operation(summary = "Employee Retrieval", description = "Retrieve all employees with page size")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content =
                    {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = ResponseDto.class))}),
            @ApiResponse(responseCode = "403", description = "User not found"),
            @ApiResponse(responseCode = "417", description = "check your input and try again"),
            @ApiResponse(responseCode = "400", description = "page size cannot be greater than 100"),
            @ApiResponse(responseCode = "500", description = "Internal server error", content =
                    {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = Problem.class))})})
    public ResponseEntity<?> getEmployees(@Valid PaginationSetting setting) {
        FindEmployeesQuery findEmployeesQuery = mapper.toFindEmployeeDto(setting);
        return ResponseEntity.ok(new ResponseDto<>(queryGateway.query(findEmployeesQuery, QueryEmployeesResponseDto.class).join()));
    }

    @GetMapping("employees/{id}")
    @Operation(summary = "Employee Retrieval", description = "Retrieve all employees with page size")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content =
                    {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = ResponseDto.class))}),
            @ApiResponse(responseCode = "417", description = "check your input and try again"),
            @ApiResponse(responseCode = "500", description = "Internal server error", content =
                    {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = Problem.class))})})
    public ResponseEntity<?> getEmployeeById(@PathVariable String id) {
        FindEmployeeQuery findEmployeeQuery = mapper.toFindEmployeeDto(id);
        return ResponseEntity.ok(new ResponseDto<>(queryGateway.query(findEmployeeQuery, EmployeeResponseDto.class).join()));
    }
}
