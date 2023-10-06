package employee.service.management.handler;
import employee.service.management.core.domain.Employee;
import employee.service.management.core.events.EmployeeCreatedEvent;
import employee.service.management.core.events.EmployeeUpdatedEvent;
import employee.service.management.query.dto.*;
import employee.service.management.query.handlers.impl.EmployeeEventHandlerImpl;
import employee.service.management.query.handlers.impl.EmployeesQueryHandlerImpl;
import employee.service.management.query.mapper.EmployeeEventHandlerMapper;
import employee.service.management.query.mapper.EmployeesQueryHandlerMapper;
import employee.service.management.query.service.EmployeeService;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class EmployeesQueryHandlerImplTest {

    @Mock
    private EmployeeService employeeService;

    @Mock
    private EmployeesQueryHandlerMapper queryHandlerMapper;

    @InjectMocks
    private EmployeesQueryHandlerImpl employeesQueryHandler;


    @Test
    void testFindEmployees() {
        FindEmployeesQuery query = new FindEmployeesQuery(new PaginationSetting(0,1));
        PageRequest page = PageRequest.of(query.paginationSetting().page(), query.paginationSetting().size());
        QueryEmployeesResponseDto expectedResponse = new QueryEmployeesResponseDto(
                List.of(
                        new EmployeeResponseDto("1", "John Doe"),
                        new EmployeeResponseDto("2", "Jane Smith"),
                        new EmployeeResponseDto("3", "Alice Johnson")
                ),
                3L,
                1,
                1,
                10
        );

        Mockito.when(employeeService.findAll(page)).thenReturn(expectedResponse);

        QueryEmployeesResponseDto response = employeesQueryHandler.findEmployees(query);

        Mockito.verify(employeeService).findAll(page);
        assertEquals(expectedResponse, response);
    }


    @Test
    void testFindEmployeeByIdWithMissingEmployee() {
        FindEmployeeQuery query = new FindEmployeeQuery(UUID.randomUUID().toString());

        Mockito.when(employeeService.findById(query.id())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> employeesQueryHandler.findEmployeeById(query));
    }
}