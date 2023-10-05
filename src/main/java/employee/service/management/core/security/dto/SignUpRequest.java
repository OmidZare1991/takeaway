package employee.service.management.core.security.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest implements Request {
  @NotBlank(message = "first name cannot be null ro empty") String firstName;
  @NotBlank(message = "last name cannot be null ro empty") String lastName;
  @Email String email;
  @NotBlank(message = "password cannot be null ro empty") String password;
}