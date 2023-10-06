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
import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class EmployeeQueryControllerTest extends IntegrationTestingBase {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private EmployeeRepository employeeRepository;


    @Test
    @Disabled
    void testEmployeeQueryHandler() {

        saveEmployee();

        URI uri = getUri();
        HttpHeaders headers = setHeaders();
        HttpEntity<Object> entity = new HttpEntity<>(headers);

        ResponseEntity<ResponseDto> response = restTemplate.exchange(uri, HttpMethod.GET, entity, ResponseDto.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }






    private URI getUri() {
        final String baseUrl = "http://localhost:" + randomServerPort + "/api/v1/employees";

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(baseUrl).queryParam("page", 0).queryParam("size", 1);
        URI uri = builder.build().toUri();
        return uri;
    }

    private HttpHeaders setHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "Bearer " + token);
        return headers;
    }

    private void saveEmployee() {
        Employee employee = new Employee();
        employee.setUuid(UUID.randomUUID().toString());
        employee.setFullName("Omid Zare");
        employee.setEmail("o.zare70@gmail.com");
        employee.setBirthdate("24.03.1991");
        employee.setHobbies(Collections.emptyList());
        employeeRepository.save(employee);
    }
}
