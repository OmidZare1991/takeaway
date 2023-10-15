package employee.service.management.command.model.dto;

import java.util.List;

public record EmployeeRequestDto(String uuid,String email, String fullName, Long birthdate, List<String> hobbies) {
}
