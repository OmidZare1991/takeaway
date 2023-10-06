package employee.service.management.core.config;

import lombok.RequiredArgsConstructor;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;

@Configuration
@EnableCaching
@RequiredArgsConstructor
public class RedisCache {

    private final Environment environment;

    @Bean
    public RedisCacheConfiguration cacheConfiguration(ConfigProperties config) {
        return RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(config.getRedisTimeToLiveMinutes())).serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
    }

    @Bean
    public RedissonClient init() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://" + environment.getProperty("spring.data.redis.host") + ":" + environment.getProperty("spring.data.redis.port"));
        return Redisson.create(config);
    }

}
