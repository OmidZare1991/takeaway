package employee.service.management.handler;

import employee.service.management.core.domain.Employee;
import employee.service.management.core.events.EmployeeCreatedEvent;
import employee.service.management.core.events.EmployeeUpdatedEvent;
import employee.service.management.query.handlers.impl.EmployeeEventHandlerImpl;
import employee.service.management.query.mapper.EmployeeEventHandlerMapper;
import employee.service.management.query.service.EmployeeService;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.OngoingStubbing;
import org.redisson.Redisson;
import org.redisson.RedissonMap;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.Collections;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeEventHandlerImplTest {

    @Mock
    private EmployeeService employeeService;

    @Mock
    private EmployeeEventHandlerMapper mapper;
    @Mock
    private RedissonClient client;
    @Mock
    private RMap<String, Employee> rMap;
    @InjectMocks
    private EmployeeEventHandlerImpl employeeEventHandler;


    @Test
    @Disabled
    void testOnEmployeeCreatedEvent() {

        EmployeeCreatedEvent event = new EmployeeCreatedEvent(
                UUID.randomUUID().toString()
                , "o.zare@gmail.com"
                , "Omid"
                , "24.03.1991"
                , Collections.emptyList()
        );
        Employee employee = getEmployee();
        when(employeeService.findById(event.uuid())).thenReturn(Optional.empty());
        OngoingStubbing<RMap<Object, Object>> rMapOngoingStubbing = when(client.getMap(anyString())).thenReturn(Mockito.any());
        RMap<Object, Object> map = client.getMap(anyString());
        when(map.get(event.email())).thenReturn(employee);
        when(mapper.toEmployee(event)).thenReturn(employee);
        employeeEventHandler.on(event);
        verify(employeeService).save(employee);
    }

    @Test
    void testOnEmployeeUpdatedEventWithMissingEmployee() {

        EmployeeUpdatedEvent event = new EmployeeUpdatedEvent(
                UUID.randomUUID().toString()
                , "o.zare@gmail.com"
                , "Omid"
                , "24.03.1991"
                , Collections.emptyList());

        when(employeeService.findById(event.uuid())).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> employeeEventHandler.on(event));
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