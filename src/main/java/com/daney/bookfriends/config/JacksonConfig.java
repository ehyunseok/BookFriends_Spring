package com.daney.bookfriends.config;

import com.daney.bookfriends.page.PageDeserializer;
import com.daney.bookfriends.page.PageSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;


//커스텀 직렬화/역직렬화 로직을 사용할 수 있도록 ObjectMapper를 설정함
@Configuration
public class JacksonConfig {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer((Class<PageImpl<?>>)(Class<?>)PageImpl.class, new PageSerializer());
        module.addDeserializer((Class<PageImpl<?>>)(Class<?>)PageImpl.class, new PageDeserializer());
        objectMapper.registerModule(module);
        return objectMapper;
    }

    @Bean
    public RedisSerializer<Object> redisSerializer(ObjectMapper objectMapper) {
        return new GenericJackson2JsonRedisSerializer(objectMapper);
    }
}