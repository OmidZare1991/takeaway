package employee.service.management.query.mapper;

import employee.service.management.core.domain.Employee;
import employee.service.management.core.events.EmployeeCreatedEvent;
import employee.service.management.core.events.EmployeeUpdatedEvent;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeEventHandlerMapper {
    Employee toEmployee(EmployeeCreatedEvent event);

    default Employee toEmployee(EmployeeUpdatedEvent event, Employee employee){
        employee.setBirthdate(event.birthdate());
        employee.setHobbies(event.hobbies());
        employee.setEmail(event.email());
        employee.setFullName(event.fullName());
        return employee;
    }
}
