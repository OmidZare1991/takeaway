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
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

@Service
@ProcessingGroup("employee-group")
@RequiredArgsConstructor
@Slf4j
public class EmployeeEventHandlerImpl implements EmployeeEventHandler {
    private final EmployeeService employeeService;
    private final EmployeeEventHandlerMapper mapper;
    private final RedissonClient redissonClient;
    private static final String REDISSON_CACHE_KEY = "employeeByIdKey";

    @Override
    @EventHandler
    public void on(EmployeeCreatedEvent event) {

        // Dear reviewer, we can discuss to whether it is good to cache or not. depends on some factors :)
        String email = event.email();

        if (employeeExistsInCache(email)) {
            log.error("employee with email: {} already exists", event.email());
            return;
        }

        Employee employee = mapper.toEmployee(event);

        employeeService.save(employee);

        addToCache(email, employee);
    }

    @Override
    @EventHandler
    public void on(EmployeeUpdatedEvent event) {
        employeeService.save(mapper.toEmployee(event, employeeService.findById(event.uuid()).orElseThrow(() -> new ResourceNotFoundException("employee not found"))));
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

    private boolean employeeExistsInCache(String email) {
        RMap<String, Employee> map = redissonClient.getMap(REDISSON_CACHE_KEY);
        return map.containsKey(email);
    }

    private void addToCache(String email, Employee employee) {
        RMap<String, Employee> map = redissonClient.getMap(REDISSON_CACHE_KEY);
        map.put(email, employee);
    }
}
