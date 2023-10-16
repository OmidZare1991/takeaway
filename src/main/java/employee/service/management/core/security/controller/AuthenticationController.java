package employee.service.management.core.security.controller;

import employee.service.management.core.base_controller.BaseController;
import employee.service.management.core.domain.dto.ResponseDto;
import employee.service.management.core.security.dto.SignInRequest;
import employee.service.management.core.security.dto.SignUpRequest;
import employee.service.management.core.security.service.JwtAuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.zalando.problem.Problem;

@RestController
@RequiredArgsConstructor
public class AuthenticationController extends BaseController {

    private final JwtAuthenticationService authenticationService;

    @PostMapping("/signup")
    @Operation(summary = "user sign up", description = "sign up to get a token to login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created", content =
                    { @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ResponseDto.class)) }),
            @ApiResponse(responseCode = "400", description = "must be a well-formed email address"),
            @ApiResponse(responseCode = "400", description = "last name cannot be null ro empty"),
            @ApiResponse(responseCode = "400", description = "first name cannot be null ro empty"),
            @ApiResponse(responseCode = "400", description = "password cannot be null ro empty"),
            @ApiResponse(responseCode = "500", description = "Internal server error") })
    public ResponseEntity<?> signup(@Valid @RequestBody SignUpRequest request) {
        return new ResponseEntity<>(new ResponseDto<>(authenticationService.signup(request)), HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    @Operation(summary = "user sign in", description = "sign in to get a token for automatic login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created", content =
                    { @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ResponseDto.class)) }),
            @ApiResponse(responseCode = "400", description = "must be a well-formed email address"),
            @ApiResponse(responseCode = "400", description = "password cannot be null ro empty"),
            @ApiResponse(responseCode = "500", description = "Internal server error") })
    public ResponseEntity<?> signin(@Valid @RequestBody SignInRequest request) {
        return new ResponseEntity<>(new ResponseDto<>(authenticationService.signin(request)), HttpStatus.CREATED);
    }
}