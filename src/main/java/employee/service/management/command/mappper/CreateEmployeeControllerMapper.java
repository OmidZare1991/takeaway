package employee.service.management.command.mappper;

import employee.service.management.command.commands.CreateEmployeeCommand;
import employee.service.management.command.model.dto.EmployeeRequest;
import employee.service.management.core.base_mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CreateEmployeeControllerMapper extends BaseMapper {
    @Mapping(target = "birthdate", qualifiedByName = "toDate", source = "birthdate")
    @Mapping(target = "uuid", expression = "java(getId())")
    CreateEmployeeCommand toCreateEmployeeCommand(EmployeeRequest request);
}
