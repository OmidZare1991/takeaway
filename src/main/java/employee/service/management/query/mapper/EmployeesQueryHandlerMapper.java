package employee.service.management.query.mapper;

import employee.service.management.core.domain.Employee;
import employee.service.management.query.dto.EmployeeResponseDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmployeesQueryHandlerMapper {
    List<EmployeeResponseDto> toEmployeeResponseDtos(List<Employee> employee);

    EmployeeResponseDto toEmployeeResponseDto(Employee employee);
}
