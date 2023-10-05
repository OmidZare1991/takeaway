package employee.service.management.core.events;

public record EmployeeTerminateEvent(String eventId, String uuid, String terminationRequestedAt, String lastDayAtWork, String reason) {
}
