package com.polixis.kafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import com.polixis.kafka.config.KafkaProducerConfig;

@SpringBootApplication
@Import(KafkaProducerConfig.class)
public class LocationTrackerApplication {

    public static void main(String[] args) {
        SpringApplication.run(LocationTrackerApplication.class, args);
    }

}
