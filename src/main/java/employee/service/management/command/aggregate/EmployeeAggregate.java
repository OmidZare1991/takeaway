package employee.service.management.command.aggregate;

import employee.service.management.command.commands.CreateEmployeeCommand;
import employee.service.management.command.commands.DeleteEmployeeCommand;
import employee.service.management.command.commands.TerminateEmployeeCommand;
import employee.service.management.command.commands.UpdateEmployeeCommand;
import employee.service.management.command.exception.DeleteEmployeeException;
import employee.service.management.command.mappper.EmployeeAggregateMapper;
import employee.service.management.core.events.EmployeeCreatedEvent;
import employee.service.management.core.events.EmployeeDeletedEvent;
import employee.service.management.core.events.EmployeeTerminateEvent;
import employee.service.management.core.events.EmployeeUpdatedEvent;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Aggregate
@NoArgsConstructor
@Slf4j
public class EmployeeAggregate {
    @AggregateIdentifier
    private String uuid;
    private String email;
    private String fullName;
    private String birthdate;
    private List<String> hobbies;
    EmployeeAggregateMapper mapper = Mappers.getMapper(EmployeeAggregateMapper.class);

    @CommandHandler
    public EmployeeAggregate(CreateEmployeeCommand command) {
        AggregateLifecycle.apply(mapper.toEmployeeCreatedEvent(command));
    }

    @EventSourcingHandler
    public void on(EmployeeCreatedEvent event) {
        this.uuid = event.uuid();
        this.email = event.email();
        this.fullName = event.fullName();
        this.birthdate = event.birthdate();
        this.hobbies = event.hobbies();
    }

    @CommandHandler
    public void handle(UpdateEmployeeCommand command) {
        AggregateLifecycle.apply(mapper.toEmployeeUpdatedEvent(command));
    }

    @EventSourcingHandler
    public void on(EmployeeUpdatedEvent event) {
        this.uuid = event.uuid();
        this.email = event.email();
        this.fullName = event.fullName();
        this.birthdate = event.birthdate();
        this.hobbies = event.hobbies();
    }

    @CommandHandler
    public void handle(DeleteEmployeeCommand command) {

        if (StringUtils.isBlank(command.uuid())) {
            throw new DeleteEmployeeException("id cannot be null or empty");
        }
        AggregateLifecycle.apply(mapper.toEmployeeDeletedEvent(command));
    }

    @EventSourcingHandler
    public void on(EmployeeDeletedEvent event) {
        AggregateLifecycle.markDeleted();
    }


    @CommandHandler
    public void handle(TerminateEmployeeCommand command) {

        if (StringUtils.isBlank(command.uuid())) {
            throw new DeleteEmployeeException("id cannot be null or empty");
        }
        AggregateLifecycle.apply(mapper.toEmployeeTerminateEvent(command));
    }

    @EventSourcingHandler
    public void on(EmployeeTerminateEvent event) {
        AggregateLifecycle.markDeleted();
    }
}
