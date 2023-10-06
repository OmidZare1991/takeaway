package employee.service.management.service;

import employee.service.management.core.domain.Employee;
import employee.service.management.query.mapper.EmployeesQueryHandlerMapper;
import employee.service.management.query.repository.EmployeeRepository;
import employee.service.management.query.service.impl.EmployeeServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@ExtendWith(MockitoExtension.class)
public class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private EmployeesQueryHandlerMapper queryHandlerMapper;

    @InjectMocks
    private EmployeeServiceImpl employeeService;


    @Test
    void testSaveEmployee() {

        Employee employee = getEmployee();

        Mockito.when(employeeRepository.save(employee)).thenReturn(employee);

        Employee savedEmployee = employeeService.save(employee);

        assertEquals(employee, savedEmployee);

        Mockito.verify(employeeRepository).save(employee);
    }


    @Test
    void testCacheForFindById() {
        String id = "123";
        Employee employee = getEmployee();

        Mockito.when(employeeRepository.findById(id)).thenReturn(Optional.of(employee));
        employeeService.findById(id);
        Mockito.verify(employeeRepository).findById(id);

        employeeService.findById(id);
        Mockito.verifyNoMoreInteractions(employeeRepository);
    }

    @Test
    void testDeleteByIdWithInvalidId() {
        String invalidId = "999";
        Mockito.doThrow(new EntityNotFoundException("Entity not found")).when(employeeRepository).deleteById(invalidId);
        assertThrows(EntityNotFoundException.class, () -> employeeService.deleteById(invalidId));
        Mockito.verify(employeeRepository).deleteById(invalidId);
    }

    private Employee getEmployee() {
        Employee employee = new Employee();
        employee.setUuid(UUID.randomUUID().toString());
        employee.setFullName("Omid Zare");
        employee.setEmail("o.zare70@gmail.com");
        employee.setBirthdate("24.03.1991");
        employee.setHobbies(Collections.emptyList());
        return employee;
    }
}





