package employee.service.management.integration;

import com.google.gson.Gson;
import com.redis.testcontainers.RedisContainer;
import employee.service.management.core.domain.dto.ResponseDto;
import employee.service.management.core.security.dto.SignUpRequest;
import org.axonframework.test.server.AxonServerContainer;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

//@Testcontainers
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntegrationTestingBase {

    @Autowired
    private TestRestTemplate restTemplate;
    @LocalServerPort
    int randomServerPort;
    String token;

//    @Container
    static AxonServerContainer axonServerContainer = new AxonServerContainer(DockerImageName.parse("axoniq/axonserver:latest-dev"));


//    @Container
//    @ServiceConnection
    static KafkaContainer kafkaContainer = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:latest")).withExposedPorts(9092, 9092).withExposedPorts(9093, 9093)// Expose the Kafka port
            .withEnv("KAFKA_ADVERTISED_LISTENERS", "PLAINTEXT://localhost:9092") // Configure advertised listeners
            .withEnv("KAFKA_LISTENERS", "PLAINTEXT://0.0.0.0:9092"); // Configure listeners


//    @Container
//    @ServiceConnection
    static MySQLContainer mySQLContainer = new MySQLContainer<>(DockerImageName.parse("mysql:latest"));


//    @Container
//    @ServiceConnection
    static MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:latest"));

//    @Container
//    @ServiceConnection
    private static final RedisContainer redisContainer = new RedisContainer(DockerImageName.parse("redis:latest")).withExposedPorts(6379);

    @BeforeEach
    void init() throws URISyntaxException {

        SignUpRequest signUpRequest = getSignUpRequest();

        URI uri = getUri();

        HttpHeaders headers = getHeaders();
        HttpEntity<SignUpRequest> request = new HttpEntity<>(signUpRequest, headers);

        ResponseEntity<ResponseDto> result = this.restTemplate.postForEntity(uri, request, ResponseDto.class);

        setToken(result);

    }

    private void setToken(ResponseEntity<ResponseDto> result) {
        Map<String, String> data = (Map) new Gson().fromJson(result.getBody().toString(), ResponseDto.class).getData();
        token = data.get("token");
    }

    @NotNull
    private static HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        return headers;
    }

    private URI getUri() throws URISyntaxException {
        final String baseUrl = "http://localhost:" + randomServerPort + "/api/v1/signup";
        return new URI(baseUrl);
    }

    private static SignUpRequest getSignUpRequest() {
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setEmail("o.zare70@gmail.com");
        signUpRequest.setPassword("afhdg");
        signUpRequest.setFirstName("Omid");
        signUpRequest.setLastName("Zare");
        return signUpRequest;
    }
}
