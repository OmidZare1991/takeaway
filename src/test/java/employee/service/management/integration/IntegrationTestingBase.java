package employee.service.management.integration;

import com.google.gson.Gson;
import com.redis.testcontainers.RedisContainer;
import employee.service.management.core.domain.dto.ResponseDto;
import employee.service.management.core.security.dto.SignUpRequest;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.axonframework.test.server.AxonServerContainer;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.JsonDeserializer;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.deser.std.StringDeserializer;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ser.std.StringSerializer;
import org.testcontainers.utility.DockerImageName;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntegrationTestingBase {

    @Autowired
    private TestRestTemplate restTemplate;
    @LocalServerPort
    int randomServerPort;
    String token;

    @Container
    static AxonServerContainer axonServerContainer = new AxonServerContainer(DockerImageName.parse("axoniq/axonserver:latest-dev"));

    @Container
    static KafkaContainer kafkaContainer = new KafkaContainer("confluentinc/cp-kafka:latest");


    @Container
    @ServiceConnection
    static MySQLContainer mySQLContainer = new MySQLContainer(DockerImageName.parse("mysql:latest"));

    @Container
    @ServiceConnection
    static MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:latest"));

    @Container
    @ServiceConnection
    private static final RedisContainer redisContainer = new RedisContainer(DockerImageName.parse("redis:latest")).withExposedPorts(6379);


    @DynamicPropertySource
    private static void registerMySqlProperties(DynamicPropertyRegistry registry) {
        registry.add("axon.axonserver.servers", axonServerContainer::getHost);
    }


    @DynamicPropertySource
    static void overrideProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.kafka.bootstrap-servers", kafkaContainer::getBootstrapServers);
        registry.add("spring.kafka.consumer.bootstrap-servers", ()->"localhost:9093");
        registry.add("spring.kafka.producer.bootstrap-servers", ()->"localhost:9093");
    }

    @BeforeEach
    void setup() throws URISyntaxException {

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
        URI uri = new URI(baseUrl);
        return uri;
    }

    private static SignUpRequest getSignUpRequest() {
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setEmail("o.zare70@gmail.com");
        signUpRequest.setPassword("afhdg");
        signUpRequest.setFirstName("Omid");
        signUpRequest.setLastName("Zare");
        return signUpRequest;
    }


    @TestConfiguration
    static class KafkaTestContainersConfiguration {

        @Bean
        ConcurrentKafkaListenerContainerFactory<Integer, String> kafkaListenerContainerFactory() {
            ConcurrentKafkaListenerContainerFactory<Integer, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
            factory.setConsumerFactory(consumerFactory());
            return factory;
        }

        @Bean
        public ConsumerFactory<Integer, String> consumerFactory() {
            return new DefaultKafkaConsumerFactory<>(consumerConfigs());
        }

        @Bean
        public Map<String, Object> consumerConfigs() {
            Map<String, Object> props = new HashMap<>();
            props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092");
            props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
//            props.put(ConsumerConfig.GROUP_ID_CONFIG, "baeldung");
            props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
            props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
            return props;
        }

        @Bean
        public ProducerFactory<String, String> producerFactory() {
            Map<String, Object> configProps = new HashMap<>();
            configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092");
            configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
            configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
            return new DefaultKafkaProducerFactory<>(configProps);
        }

        @Bean
        public KafkaTemplate<String, String> kafkaTemplate() {
            return new KafkaTemplate<>(producerFactory());
        }

    }
}
