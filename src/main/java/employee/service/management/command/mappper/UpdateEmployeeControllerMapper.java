package employee.service.management.command.mappper;

import employee.service.management.command.commands.UpdateEmployeeCommand;
import employee.service.management.command.dto.EmployeeRequest;
import employee.service.management.core.base_mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UpdateEmployeeControllerMapper extends BaseMapper {
    @Mapping(target = "birthdate", qualifiedByName = "toDate", source = "request.birthdate")
    @Mapping(target = "uuid", source = "id")
//    @Mapping(target = "version", ignore = true)
    UpdateEmployeeCommand toUpdateEmployeeCommand(EmployeeRequest request,String id);
}
