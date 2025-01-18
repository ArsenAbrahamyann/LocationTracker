package com.polixis.kafka.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * Configuration class for Kafka producer setup in a Spring context.
 * This class defines beans for creating a Kafka Producer Factory and a Kafka Template configured for String serialization.
 * This setup is typically used for sending messages to Kafka topics where both keys and values are Strings.
 */
@Configuration
public class KafkaProducerConfig {

    /**
     * Creates a {@link ProducerFactory} bean specifically configured for producing string-keyed and string-valued messages.
     * The factory configures the necessary producer properties such as bootstrap servers and serializers.
     *
     * @return A {@link ProducerFactory} with configurations suitable for string data.
     */
    @Bean
    public ProducerFactory<String, String> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    /**
     * Creates a {@link KafkaTemplate} for sending String messages to Kafka.
     * This method sets up a {@link KafkaTemplate} using the previously defined {@link ProducerFactory}.
     * A KafkaTemplate provides a high-level abstraction for sending messages.
     *
     * @return A configured {@link KafkaTemplate<String, String>} ready for use to send string messages to Kafka.
     */
    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(configProps));
    }
}
