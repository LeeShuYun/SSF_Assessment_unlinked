package dev.leeshuyun.SSF_Assessment.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;


@Configuration
public class RedisConfig {

    // @Value links to application properties
// ## app.properties Local settings. use "set REDISHOST=localhost" and "set REDISPORT=8080" on windows to make this work
// spring.redis.host=${REDISHOST}
// spring.redis.port=${REDISPORT}
// spring.redis.username=
// spring.redis.password=
// spring.redis.client.type=jedis
    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private Optional<Integer> redisPort;

    @Value("${spring.redis.username}")
    private String redisUsername;

    @Value("${spring.redis.password}")
    private String redisPassword;


    // define Redis Template bean as single static Object throughout the runtime.
    // Returns the RedisTemplate
    @Bean
    @Scope("singleton")
    public RedisTemplate<String, Object> redisTemplate(){

        final RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();

        //setting the config for redistemplate
        config.setHostName(redisHost);
        config.setPort(redisPort.get());
        System.out.println(redisUsername);
        System.out.println(redisPassword);
        
        if(!redisUsername.isEmpty() && !redisPassword.isEmpty()){
            config.setUsername(redisUsername);
            config.setPassword(redisPassword);
        }
        //chosind database 0
        config.setDatabase(0);

        // JedisConnectionFactory should be configured using an environmental
        // configuration and the client configuration.
        // we use RedisStandaloneConfiguration config for this configuration
        // This connection factory must be initialized prior to obtaining connections.
        final JedisClientConfiguration jedisClient = JedisClientConfiguration
                                        .builder()
                                        .build();
        final JedisConnectionFactory jedisFac= 
                            new JedisConnectionFactory(config, jedisClient);
        jedisFac.afterPropertiesSet();
        //So redis actually uses binary data internally, so we need to serialize everytime we insert or get data from it
        // RedisTemplate<K,V> where K is the key and V is the value. 
        RedisTemplate<String, Object> redisTemplate = 
                    new RedisTemplate<String, Object>();
        // associate with the redis connection
        redisTemplate.setConnectionFactory(jedisFac);

        //now we serialise the keys for the KVstore. 
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        // set the map key/value serialization type to String
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new StringRedisSerializer());
        
        return redisTemplate;
    }
}
