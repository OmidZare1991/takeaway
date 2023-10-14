package employee.service.management.command.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;
import org.axonframework.modelling.command.TargetAggregateVersion;

import java.util.List;

public record CreateEmployeeCommand(
        @TargetAggregateIdentifier String uuid
//        ,@TargetAggregateVersion long version
        , String email
        , String fullName
        ,String birthdate
        , List<String> hobbies
) {
}
