package employee.service.management.query.handlers;

import employee.service.management.query.dto.EmployeeResponseDto;
import employee.service.management.query.dto.FindEmployeeQuery;
import employee.service.management.query.dto.FindEmployeesQuery;
import employee.service.management.query.dto.QueryEmployeesResponseDto;

public interface EmployeesQueryHandler {
    QueryEmployeesResponseDto findEmployees(FindEmployeesQuery query);
    EmployeeResponseDto findEmployeeById(FindEmployeeQuery query);
}
