package employee.service.management.query.handlers.impl;

import employee.service.management.query.dto.EmployeeResponseDto;
import employee.service.management.query.dto.FindEmployeeQuery;
import employee.service.management.query.dto.FindEmployeesQuery;
import employee.service.management.query.dto.QueryEmployeesResponseDto;
import employee.service.management.query.handlers.EmployeesQueryHandler;
import employee.service.management.query.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmployeesQueryHandlerImpl implements EmployeesQueryHandler {
    private final EmployeeService employeeService;

    @Override
    @QueryHandler
    public QueryEmployeesResponseDto findEmployees(FindEmployeesQuery query) {
        PageRequest page = PageRequest.of(query.paginationSetting().page(), query.paginationSetting().size());
        return employeeService.findAll(page);
    }

    @Override
    @QueryHandler
    @Cacheable(value = "employee", key = "#query.id()")
    public EmployeeResponseDto findEmployeeById(FindEmployeeQuery query) {
        return employeeService.findById(query.id());
    }
}
