package employee.service.management.query.service.impl;

import employee.service.management.core.domain.Employee;
import employee.service.management.query.dto.QueryEmployeesResponseDto;
import employee.service.management.query.mapper.EmployeesQueryHandlerMapper;
import employee.service.management.query.repository.EmployeeRepository;
import employee.service.management.query.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final EmployeesQueryHandlerMapper queryHandlerMapper;

    @Override
    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    @Cacheable(value = "cacheById",key = "#id")
    public Optional<Employee> findById(String id) {
        return employeeRepository.findById(id);
    }

    @Override
    @Cacheable(cacheNames = "employees", key = "#page.pageNumber")
    public QueryEmployeesResponseDto findAll(PageRequest page) {
        Page<Employee> employees = employeeRepository.findAll(page);

        return new QueryEmployeesResponseDto(
                queryHandlerMapper.toEmployeeResponseDtos(
                        employees.getContent())
                , employees.getTotalElements()
                , employees.getTotalPages()
                , employees.getNumber()
                , employees.getSize()
        );
    }

    @Override
    public void deleteById(String id) {
        employeeRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        employeeRepository.deleteAll();
    }
}
