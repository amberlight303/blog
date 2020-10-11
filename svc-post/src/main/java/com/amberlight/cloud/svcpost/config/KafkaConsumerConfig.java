package com.amberlight.cloud.svcpost.config;

import com.amberlight.cloud.svcpost.post.model.domain.Post;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.DefaultKafkaHeaderMapper;
import org.springframework.kafka.support.converter.BatchMessagingMessageConverter;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

    @Value(value = "${kafka.bootstrapAddress}")
    private String bootstrapAddress;

    @Value(value = "${kafka.consumersGroup.elasticsearch.groupId}")
    private String elasticsearchGroupId;

    public ConsumerFactory<String, String> consumerFactory(String groupId) {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(props);
    }

    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory(String groupId) {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory(groupId));
        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> elasticsearchKafkaListenerContainerFactory() {
        return kafkaListenerContainerFactory(elasticsearchGroupId);
    }

    @Bean
    public DefaultKafkaHeaderMapper headerMapper(){
        return new DefaultKafkaHeaderMapper();
    }

//    @Bean
//    public ConsumerFactory<String, Post> consumerFactory() {
//        return new DefaultKafkaConsumerFactory<>(consumerConfigs(elasticsearchGroupId));
//    }
//
//    public Map<String, Object> consumerConfigs(String groupId) {
//        Map<String, Object> props = new HashMap<>();
//        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
//        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
//        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//        return props;
//    }


//    @Bean
//    public ConcurrentKafkaListenerContainerFactory<String, Post> batchFactory() {
//        ConcurrentKafkaListenerContainerFactory<String, Post> factory =
//                new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(consumerFactory());
//        factory.setBatchListener(true);
//        factory.setMessageConverter(new BatchMessagingMessageConverter(stringJsonMessageConverter()));
//        return factory;
//    }
//
//    @Bean
//    public ConcurrentKafkaListenerContainerFactory<String, Post> singleFactory() {
//        ConcurrentKafkaListenerContainerFactory<String, Post> factory =
//                new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(consumerFactory());
//        factory.setBatchListener(false);
//        factory.setMessageConverter(new StringJsonMessageConverter());
//        return factory;
//    }


//    @Bean
//    public StringJsonMessageConverter stringJsonMessageConverter() {
//        return new StringJsonMessageConverter();
//    }

















//    @Bean
//    public ConcurrentKafkaListenerContainerFactory<String, String> barKafkaListenerContainerFactory() {
//        return kafkaListenerContainerFactory("bar");
//    }
//
//    @Bean
//    public ConcurrentKafkaListenerContainerFactory<String, String> headersKafkaListenerContainerFactory() {
//        return kafkaListenerContainerFactory("headers");
//    }
//
//    @Bean
//    public ConcurrentKafkaListenerContainerFactory<String, String> partitionsKafkaListenerContainerFactory() {
//        return kafkaListenerContainerFactory("partitions");
//    }

//    @Bean
//    public ConcurrentKafkaListenerContainerFactory<String, String> filterKafkaListenerContainerFactory() {
//        ConcurrentKafkaListenerContainerFactory<String, String> factory = kafkaListenerContainerFactory("filter");
//        factory.setRecordFilterStrategy(record -> record.value()
//                .contains("World"));
//        return factory;
//    }

//    public ConsumerFactory<String, Greeting> greetingConsumerFactory() {
//        Map<String, Object> props = new HashMap<>();
//        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
//        props.put(ConsumerConfig.GROUP_ID_CONFIG, "greeting");
//        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new JsonDeserializer<>(Greeting.class));
//    }

//    @Bean
//    public ConcurrentKafkaListenerContainerFactory<String, Greeting> greetingKafkaListenerContainerFactory() {
//        ConcurrentKafkaListenerContainerFactory<String, Greeting> factory = new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(greetingConsumerFactory());
//        return factory;
//    }

}
