package com.polixis.kafka.config;

import java.util.HashMap;
import java.util.Map;

import javax.xml.stream.Location;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

/**
 * Configuration class for Kafka consumer setup.
 * This class handles the creation of consumer factories and listener containers
 * that are necessary for consuming messages from Kafka topics.
 */
@Configuration
public class KafkaConsumerConfig {

    /**
     * Creates and configures a {@link ConsumerFactory} that supports consuming messages with
     * {@link String} keys and {@link Location} values from Kafka.
     *
     * @return A {@link ConsumerFactory} set up with {@link StringDeserializer} for keys
     * and {@link JsonDeserializer} for values which deserializes {@link Location} objects.
     */
    @Bean
    public ConsumerFactory<String, Location> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "location-tracker");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        JsonDeserializer<Location> deserializer = new JsonDeserializer<>(Location.class);
        deserializer.addTrustedPackages("*");

        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), deserializer);
    }

    /**
     * Creates a {@link ConcurrentKafkaListenerContainerFactory} for building Kafka listener containers.
     * This factory is configured to use the custom {@link ConsumerFactory} which is capable of
     * deserializing Kafka records into {@link Location} objects.
     *
     * @return a fully configured {@link ConcurrentKafkaListenerContainerFactory} for {@link Location} type.
     */
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Location> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Location> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}
