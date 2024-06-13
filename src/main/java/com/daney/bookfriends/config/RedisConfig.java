package com.daney.bookfriends.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisScriptingCommands;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

//Redis를 사용하기 위한 설정
@Configuration
@EnableCaching  // 캐시 추상화 활성화
public class RedisConfig {

    @Value("${spring.data.redis.host}")
    private String redisHost;

    @Value("${spring.data.redis.port}")
    private int redisPort;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() { //Redis와 연결을 생성하고 관리하는 인터페이스

        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        config.setHostName(redisHost);
        config.setPort(redisPort);


        return new LettuceConnectionFactory();
        /*Lettuce는 Redis 클라이언트 라이브러리. 비동기 및 동기 방식 모두 지원
          이 클래스는 Lettuce를 사용하여 Redis 연결을 생성함 */
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() { // Redis에 데이터를 저장하고 가져오는 템플릿 클래스
        // redis에 데이터를 저장하고 가져오는 템플릿 클래스. 다양한 redis 데이터 타입을 다룰 수 있다.
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        // 이 템플릿이 redis와 통신할 때 사용할 연결 팩토리를 설정함
        template.setConnectionFactory(redisConnectionFactory());
        // redis 키를 직렬화하는 방법을 설명함. 'StringRedisSerializer'를 사용하여 키를 문자열로 직렬화한다.
        template.setKeySerializer(new StringRedisSerializer());
        // redis 값을 질렬화 하는 방법을 설정한다. 'GenericJackson2JsonRedisSerializer'를 사용하여 json 형식으로 직렬화함
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return template;
    }


}
