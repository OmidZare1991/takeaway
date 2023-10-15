package employee.service.management.command.controller;

import employee.service.management.command.commands.DeleteEmployeeCommand;
import employee.service.management.core.base_controller.BaseController;
import employee.service.management.core.domain.dto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class DeleteEmployeeController extends BaseController {
    private final CommandGateway commandGateway;

    @DeleteMapping("employee/{id}")
    @Operation(summary = "Employee delete", description = "delete the existing Employee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content =
                    { @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ResponseDto.class)) }),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "forbidden"),
            @ApiResponse(responseCode = "500", description = "Internal server error")})
    public ResponseEntity<?> deleteEmployee(@PathVariable String id){
        commandGateway.sendAndWait(new DeleteEmployeeCommand(id));
        return ResponseEntity.ok(new ResponseDto<>("deleting the employee done successfully"));
    }
}
