package employee.service.management.command.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public record DeleteEmployeeCommand(@TargetAggregateIdentifier String uuid) {
}
