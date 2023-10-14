package employee.service.management;

import employee.service.management.command.commands.CreateEmployeeCommand;
import employee.service.management.command.commands.UpdateEmployeeCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

@SpringBootApplication
public class EmployeeServiceManagementApplication implements CommandLineRunner {
	@Autowired
	private CommandGateway gateway;

	public static void main(String[] args) {
		SpringApplication.run(EmployeeServiceManagementApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		String uuid = UUID.randomUUID().toString();

//		Object jafar = gateway.sendAndWait(new CreateEmployeeCommand(uuid, "o.zare70@gmail.com", "jafar", "23-04-991", Collections.emptyList()));
//
//		ExecutorService executorService = Executors.newFixedThreadPool(2);
//		AtomicInteger i = new AtomicInteger();
//		executorService.submit(()->{
//			UpdateEmployeeCommand command = new UpdateEmployeeCommand(uuid, "o.zare70@gmail.com", "mohammad"+i, "23-04-991", Collections.emptyList());
//			i.getAndIncrement();
//			gateway.sendAndWait(command);
//		});
//
//		System.out.println("hey");
	}
}
