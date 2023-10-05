package employee.service.management.query.mapper;

import employee.service.management.query.dto.FindEmployeeQuery;
import employee.service.management.query.dto.FindEmployeesQuery;
import employee.service.management.query.dto.PaginationSetting;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeEventControllerMapper {
    FindEmployeesQuery toFindEmployeeDto(PaginationSetting paginationSetting);
    FindEmployeeQuery toFindEmployeeDto(String id);
}
