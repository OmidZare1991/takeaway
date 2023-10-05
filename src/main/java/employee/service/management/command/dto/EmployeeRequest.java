package employee.service.management.command.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record EmployeeRequest(@Email String email, @NotBlank(message = "full name cannot be null or empty") String fullName, @NotNull(message = "birthdate cannot be null or empty") Long birthdate, List<String> hobbies) {
}
