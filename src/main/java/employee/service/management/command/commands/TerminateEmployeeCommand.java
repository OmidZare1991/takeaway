package employee.service.management.command.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public record TerminateEmployeeCommand(String eventId, @TargetAggregateIdentifier String uuid, String terminationRequestedAt, String lastDayAtWork, String reason) {
}
