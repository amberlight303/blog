package com.amberlight.cloud.gateway.config;


//@Configuration
//@EnableCaching
public class RedisConfig {

//    @Bean
//    public RedisTemplate<Object, Object> sessionRedisTemplate(RedisConnectionFactory connectionFactory) {
//        RedisTemplate<Object, Object> template = new RedisTemplate<Object, Object>();
//        template.setKeySerializer(new StringRedisSerializer());
//        template.setHashKeySerializer(new StringRedisSerializer());
//        template.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());
//        template.setConnectionFactory(connectionFactory);
//        return template;
//    }
//
//    @Bean
//    @Primary
//    public RedisCacheConfiguration defaultCacheConfig(ObjectMapper objectMapper) {
//        return RedisCacheConfiguration.defaultCacheConfig()
//                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
//                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer(objectMapper)));
//    }

}
