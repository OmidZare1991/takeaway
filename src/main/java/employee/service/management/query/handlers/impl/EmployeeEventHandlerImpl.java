package employee.service.management.query.handlers.impl;


import employee.service.management.core.domain.Employee;
import employee.service.management.core.events.EmployeeCreatedEvent;
import employee.service.management.core.events.EmployeeDeletedEvent;
import employee.service.management.core.events.EmployeeTerminateEvent;
import employee.service.management.core.events.EmployeeUpdatedEvent;
import employee.service.management.query.handlers.EmployeeEventHandler;
import employee.service.management.query.mapper.EmployeeEventHandlerMapper;
import employee.service.management.query.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.ResetHandler;
import org.axonframework.messaging.interceptors.ExceptionHandler;
import org.springframework.stereotype.Service;

@Service
@ProcessingGroup("employee-group")
@RequiredArgsConstructor
@Slf4j
public class EmployeeEventHandlerImpl implements EmployeeEventHandler {
    private final EmployeeService employeeService;
    private final EmployeeEventHandlerMapper mapper;

    @Override
    @EventHandler
    public void on(EmployeeCreatedEvent event) {
        Employee employee = mapper.toEmployee(event);
        employeeService.save(employee);
    }

    @Override
    @EventHandler
    public void on(EmployeeUpdatedEvent event) throws IllegalArgumentException{

        Employee employee = employeeService.findById(event.uuid()).orElseThrow(() -> new ResourceNotFoundException("employee not found"));
        employeeService.save(mapper.toEmployee(event, employee));

    }

    @Override
    @EventHandler
    public void on(EmployeeDeletedEvent event) {
        employeeService.deleteById(event.uuid());
    }

    @Override
    @EventHandler
    public void on(EmployeeTerminateEvent event) {
        employeeService.deleteById(event.uuid());
    }

    @ExceptionHandler
    public void handle(Exception e) throws Exception {
        throw e;
    }

    @ResetHandler
    public void reset(){
        employeeService.deleteAll();
    }
}
