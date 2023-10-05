package employee.service.management.core.events;

import java.util.List;

public record EmployeeUpdatedEvent(String uuid, String email, String fullName, String birthdate, List<String> hobbies) {
}
