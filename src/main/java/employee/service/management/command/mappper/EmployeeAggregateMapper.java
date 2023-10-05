package employee.service.management.command.mappper;

import employee.service.management.command.commands.CreateEmployeeCommand;
import employee.service.management.command.commands.DeleteEmployeeCommand;
import employee.service.management.command.commands.TerminateEmployeeCommand;
import employee.service.management.command.commands.UpdateEmployeeCommand;
import employee.service.management.core.events.EmployeeCreatedEvent;
import employee.service.management.core.events.EmployeeDeletedEvent;
import employee.service.management.core.events.EmployeeTerminateEvent;
import employee.service.management.core.events.EmployeeUpdatedEvent;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeAggregateMapper {
    EmployeeCreatedEvent toEmployeeCreatedEvent(CreateEmployeeCommand command);

    EmployeeUpdatedEvent toEmployeeUpdatedEvent(UpdateEmployeeCommand command);

    EmployeeDeletedEvent toEmployeeDeletedEvent(DeleteEmployeeCommand command);
    EmployeeTerminateEvent toEmployeeTerminateEvent(TerminateEmployeeCommand command);
}
