package employee.service.management.command.mappper;

import employee.service.management.command.model.EmployeeLookUpEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeLookupMapper {
    EmployeeLookUpEntity toEmployeeLookUpEntity(String email);
}
