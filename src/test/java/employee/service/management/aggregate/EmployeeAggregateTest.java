package employee.service.management.aggregate;

import employee.service.management.command.aggregate.EmployeeAggregate;
import employee.service.management.command.commands.DeleteEmployeeCommand;
import employee.service.management.command.commands.UpdateEmployeeCommand;
import employee.service.management.core.events.EmployeeCreatedEvent;
import employee.service.management.core.events.EmployeeDeletedEvent;
import employee.service.management.core.events.EmployeeUpdatedEvent;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.UUID;

public class EmployeeAggregateTest {
    private FixtureConfiguration<EmployeeAggregate> employeeAggregate;


    @BeforeEach
    public void setup() {
        employeeAggregate = new AggregateTestFixture<>(EmployeeAggregate.class);
    }

    @Test
    void testUpdateEmployeeCommand() {
        String uuid = UUID.randomUUID().toString();

        EmployeeCreatedEvent event = new EmployeeCreatedEvent(uuid, "o.zare@gmail.com", "Mohammad Zare", "24.03.1991", Collections.emptyList());

        UpdateEmployeeCommand command = new UpdateEmployeeCommand(uuid, "o.zare@gmail.com", "Omid Zare", "24.03.1991", Collections.emptyList());

        EmployeeUpdatedEvent updatedEvent = new EmployeeUpdatedEvent(uuid, "o.zare@gmail.com", "Omid Zare", "24.03.1991", Collections.emptyList());

        employeeAggregate.given(event).when(command).expectSuccessfulHandlerExecution().expectEvents(updatedEvent);
    }

    @Test
    void testDeleteEmployeeCommand() {
        String uuid = UUID.randomUUID().toString();

        EmployeeCreatedEvent event = new EmployeeCreatedEvent(uuid, "o.zare@gmail.com", "Omid Zare", "24.03.1991", Collections.emptyList());


        DeleteEmployeeCommand command = new DeleteEmployeeCommand(uuid);


        EmployeeDeletedEvent deletedEvent = new EmployeeDeletedEvent(uuid);


        employeeAggregate.given(event).when(command).expectSuccessfulHandlerExecution().expectEvents(deletedEvent);
    }
}
