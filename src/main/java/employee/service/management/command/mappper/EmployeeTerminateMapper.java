package employee.service.management.command.mappper;

import employee.service.management.command.commands.TerminateEmployeeCommand;
import employee.service.management.core.events.EmployeeEndEvent;
import employee.service.management.core.events.EmployeeTerminateEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EmployeeTerminateMapper {
    @Mapping(target = "uuid",source = "employeeId")
    EmployeeTerminateEvent toEmployeeTerminateEvent(EmployeeEndEvent event);
    TerminateEmployeeCommand toTerminateEmployeeCommand(EmployeeTerminateEvent event);
}
