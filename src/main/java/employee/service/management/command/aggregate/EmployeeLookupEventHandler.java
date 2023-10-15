package employee.service.management.command.aggregate;

import employee.service.management.command.mappper.EmployeeLookupMapper;
import employee.service.management.command.repository.EmployeeLookupRepository;
import employee.service.management.core.events.EmployeeCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@ProcessingGroup("employee-group")
public class EmployeeLookupEventHandler {
    private final EmployeeLookupRepository employeeLookupRepository;
    private final EmployeeLookupMapper mapper;

    @EventHandler
    public void on(EmployeeCreatedEvent event) {
        employeeLookupRepository.save(mapper.toEmployeeLookUpEntity(event.email()));
    }
}
