package employee.service.management.command.handlers;

import employee.service.management.command.mappper.EmployeeTerminateMapper;
import employee.service.management.core.events.EmployeeEndEvent;
import employee.service.management.core.events.EmployeeTerminateEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaHandler {
    private final CommandGateway commandGateway;
    private final EmployeeTerminateMapper mapper;

    @KafkaListener(topics = "${takeaway.kafka.topic.name}")
    public void consume(EmployeeEndEvent event) {

        EmployeeTerminateEvent terminateEvent = mapper.toEmployeeTerminateEvent(event);

        commandGateway.send(mapper.toTerminateEmployeeCommand(terminateEvent), (commandMessage, commandResultMessage) -> {
            if (commandResultMessage.isExceptional()) {
                log.error("Employee termination event failed", commandResultMessage.exceptionResult());
            }
        });
    }
}
