package employee.service.management.mapper;

import employee.service.management.command.commands.TerminateEmployeeCommand;
import employee.service.management.command.mappper.EmployeeAggregateMapper;
import employee.service.management.core.events.EmployeeTerminateEvent;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmployeeAggregateMapperTest {
    EmployeeAggregateMapper mapper = Mappers.getMapper(EmployeeAggregateMapper.class);

    @Test
    void testEmployeeAggregateMapper() {
        TerminateEmployeeCommand command = new TerminateEmployeeCommand("eventId-Test", "uuid-Test", "2008", "Monday", "Immigration");

        EmployeeTerminateEvent event = mapper.toEmployeeTerminateEvent(command);
        assertEquals("eventId-Test", event.eventId());
        assertEquals("uuid-Test", event.uuid());
        assertEquals("2008", event.terminationRequestedAt());
        assertEquals("Monday", event.lastDayAtWork());
        assertEquals("Immigration", event.reason());
    }
}
