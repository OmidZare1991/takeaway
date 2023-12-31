package employee.service.management.command.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.List;

public record UpdateEmployeeCommand(
        @TargetAggregateIdentifier String uuid
        , String email
        , String fullName
        , String birthdate
        , List<String> hobbies
) {
}
