package employee.service.management.core.events;

public record EmployeeEndEvent(String eventId, String employeeId, String terminationRequestedAt, String lastDayAtWork, String reason) {
}
