package employee.service.management.integration;

import employee.service.management.command.dto.EmployeeRequest;
import employee.service.management.core.domain.Employee;
import employee.service.management.core.domain.dto.ResponseDto;
import employee.service.management.query.repository.EmployeeRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CreateEmployeeControllerTest extends IntegrationTestingBase {
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    @Disabled
    void testCreateEmployeeController() throws URISyntaxException {

        EmployeeRequest employeeRequest = new EmployeeRequest("o.zare70@gmail.com", "Omid Zare", 669851059000L, Collections.emptyList());

        final String baseUrl = "http://localhost:" + randomServerPort + "/api/v1/employee";
        URI uri = new URI(baseUrl);
        HttpHeaders httpHeaders = setHeaders();

        HttpEntity<EmployeeRequest> entity = new HttpEntity<>(employeeRequest, httpHeaders);
        ResponseEntity<ResponseDto> response = restTemplate.exchange(uri, HttpMethod.POST, entity, ResponseDto.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        assertNotNull(response.getBody());


        List<Employee> employees = employeeRepository.findAll();

        assertEquals(1,employees.size());
        assertEquals("Omid Zare",employees.get(0).getFullName());

    }

    private HttpHeaders setHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "Bearer " + token);
        return headers;
    }

}
