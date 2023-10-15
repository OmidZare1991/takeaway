package employee.service.management;

import employee.service.management.core.interceptor.CreateEmployeeCommandInterceptor;
import employee.service.management.core.handler.EmployeeEventErrorHandler;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.config.EventProcessingConfigurer;
import org.axonframework.eventsourcing.EventCountSnapshotTriggerDefinition;
import org.axonframework.eventsourcing.SnapshotTriggerDefinition;
import org.axonframework.eventsourcing.Snapshotter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class EmployeeServiceManagementApplication {
    public static void main(String[] args) {
        SpringApplication.run(EmployeeServiceManagementApplication.class, args);
    }

    @Autowired
    public void configure(EventProcessingConfigurer configurer) {
        configurer.registerListenerInvocationErrorHandler("employee-group", conf -> new EmployeeEventErrorHandler());
    }

    @Autowired
    public void registerCreateEmployeeCommandInterceptor(ApplicationContext context, CommandBus commandBus){
        commandBus.registerDispatchInterceptor(context.getBean(CreateEmployeeCommandInterceptor.class));
    }
    @Bean(name = "employeeSnapshotTriggerDefinition")
    public SnapshotTriggerDefinition employeeSnapshotTriggerDefinition(Snapshotter snapshotter){
        return new EventCountSnapshotTriggerDefinition(snapshotter,3);
    }
}
