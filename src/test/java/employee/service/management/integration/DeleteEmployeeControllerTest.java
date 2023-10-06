package employee.service.management.integration;

import employee.service.management.core.domain.Employee;
import employee.service.management.core.domain.dto.ResponseDto;
import employee.service.management.query.repository.EmployeeRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeleteEmployeeControllerTest extends IntegrationTestingBase {
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    @Disabled
    void testDeleteEmployeeController() throws URISyntaxException {
        String id = UUID.randomUUID().toString();
        Employee employee = new Employee();
        employee.setUuid(id);
        employee.setBirthdate("24.03.1991");
        employee.setFullName("Omid Zare");
        employee.setEmail("o.zare70@gmail.com");
        employeeRepository.save(employee);



        URI uri = UriComponentsBuilder.fromUriString("http://localhost:" + randomServerPort + "/api/v1/employee/{id}")
                .buildAndExpand(id)
                .toUri();

        HttpHeaders httpHeaders = setHeaders();

        HttpEntity<Object> entity = new HttpEntity<>(httpHeaders);

        ResponseEntity<ResponseDto> response = restTemplate.exchange(uri, HttpMethod.DELETE, entity, ResponseDto.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        List<Employee> employees = employeeRepository.findAll();

        assertEquals(0,employees.size());
    }
    private HttpHeaders setHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "Bearer " + token);
        return headers;
    }
}
