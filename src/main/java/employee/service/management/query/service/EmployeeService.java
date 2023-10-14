package employee.service.management.query.service;

import employee.service.management.core.domain.Employee;
import employee.service.management.query.dto.QueryEmployeesResponseDto;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

public interface EmployeeService {

    Employee save(Employee employee);

    Optional<Employee> findById(String id);

    QueryEmployeesResponseDto findAll(PageRequest page);
    void deleteById(String id);

}
