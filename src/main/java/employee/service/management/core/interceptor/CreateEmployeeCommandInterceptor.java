package employee.service.management.core.interceptor;

import employee.service.management.command.commands.CreateEmployeeCommand;
import employee.service.management.command.repository.EmployeeLookupRepository;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.function.BiFunction;

@Component
@RequiredArgsConstructor
public class CreateEmployeeCommandInterceptor implements MessageDispatchInterceptor<CommandMessage<?>> {
    private final EmployeeLookupRepository repository;

    @Nonnull
    @Override
    public BiFunction<Integer, CommandMessage<?>, CommandMessage<?>> handle(@Nonnull List<? extends CommandMessage<?>> list) {
        return (index, command) -> {
            if (CreateEmployeeCommand.class.equals(command.getPayloadType())) {
                CreateEmployeeCommand createEmployeeCommand = (CreateEmployeeCommand) command.getPayload();
                // do validation and throw exceptions
//                synchronized (CreateEmployeeCommandInterceptor.class) {
                    if (repository.findByEmail(createEmployeeCommand.email()) != null) {
                        throw new IllegalStateException(String.format("employee with email %s already exist", createEmployeeCommand.email()));
                    }
//                }
            }
            return command;
        };
    }
}
