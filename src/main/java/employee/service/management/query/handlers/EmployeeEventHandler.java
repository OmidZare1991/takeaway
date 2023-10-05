package employee.service.management.query.handlers;

import employee.service.management.core.events.EmployeeCreatedEvent;
import employee.service.management.core.events.EmployeeDeletedEvent;
import employee.service.management.core.events.EmployeeTerminateEvent;
import employee.service.management.core.events.EmployeeUpdatedEvent;

public interface EmployeeEventHandler {
    void on(EmployeeCreatedEvent event);
    void on(EmployeeUpdatedEvent event);
    void on(EmployeeDeletedEvent event);
    void on(EmployeeTerminateEvent event);
}
