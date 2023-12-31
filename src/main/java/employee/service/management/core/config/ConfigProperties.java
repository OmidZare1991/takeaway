package employee.service.management.core.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "takeaway")
@Setter
@Getter
public class ConfigProperties {
    private Long redisTimeToLiveMinutes;
    private String redisHost;
    private Integer redisPort;
    private Long redisConnectionTimeOutMillis;
    private String tokenSecretKey;
    private Long jwtExpirationMillis;
}