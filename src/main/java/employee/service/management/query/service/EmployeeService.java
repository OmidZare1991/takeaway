package employee.service.management.query.service;

import employee.service.management.core.domain.Employee;
import employee.service.management.query.dto.EmployeeResponseDto;
import employee.service.management.query.dto.QueryEmployeesResponseDto;
import org.springframework.data.domain.PageRequest;

public interface EmployeeService {

    Employee save(Employee employee);

    EmployeeResponseDto findById(String id);

    Employee getEmployeeById(String id);

    QueryEmployeesResponseDto findAll(PageRequest page);

    void deleteById(String id);
}
