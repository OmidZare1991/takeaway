package employee.service.management.core.security.service;

import employee.service.management.core.security.dto.Response;
import employee.service.management.core.security.dto.SignInRequest;
import employee.service.management.core.security.dto.SignUpRequest;

public interface AuthenticationService<RESPONSE extends Response> {
    RESPONSE signup(SignUpRequest request);

    RESPONSE signin(SignInRequest request);
}
