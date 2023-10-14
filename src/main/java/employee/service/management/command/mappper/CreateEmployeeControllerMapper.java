package employee.service.management.command.mappper;

import employee.service.management.command.commands.CreateEmployeeCommand;
import employee.service.management.command.dto.EmployeeRequest;
import employee.service.management.core.base_mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CreateEmployeeControllerMapper extends BaseMapper {
    @Mapping(target = "birthdate", qualifiedByName = "toDate", source = "birthdate")
    @Mapping(target = "uuid", expression = "java(getId())")
//    @Mapping(target = "version", ignore = true)
    CreateEmployeeCommand toCreateEmployeeCommand(EmployeeRequest request);
}
