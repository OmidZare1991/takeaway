package employee.service.management.query.handlers.impl;

import employee.service.management.core.domain.Employee;
import employee.service.management.query.dto.EmployeeResponseDto;
import employee.service.management.query.dto.FindEmployeeQuery;
import employee.service.management.query.dto.FindEmployeesQuery;
import employee.service.management.query.dto.QueryEmployeesResponseDto;
import employee.service.management.query.handlers.EmployeesQueryHandler;
import employee.service.management.query.mapper.EmployeesQueryHandlerMapper;
import employee.service.management.query.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmployeesQueryHandlerImpl implements EmployeesQueryHandler {
    private final EmployeeService employeeService;
    private final EmployeesQueryHandlerMapper queryHandlerMapper;

    @Override
    @QueryHandler
    public QueryEmployeesResponseDto findEmployees(FindEmployeesQuery query) {
        PageRequest page = PageRequest.of(query.paginationSetting().page(), query.paginationSetting().size());
        return employeeService.findAll(page);
//        return employeeService.findEmployeesWithPagination(page);
    }

    @Override
    @QueryHandler
    public EmployeeResponseDto findEmployeeById(FindEmployeeQuery query) {
        Employee employee = employeeService.findById(query.id()).orElseThrow(() -> new ResourceNotFoundException("employee not found"));
        return queryHandlerMapper.toEmployeeResponseDto(employee);
    }
}
