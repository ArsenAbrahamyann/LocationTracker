package com.polixis.kafka.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Random;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.polixis.kafka.model.LocationData;

/**
 * Service class for simulating and emitting location data at fixed intervals.
 * This class periodically generates simulated geographical coordinates and sends these locations as messages to a Kafka topic.
 * It's useful for testing or demonstrating realtime location tracking features.
 */
@Service
@Slf4j
public class LocationSimulator {

    @Autowired
    @Qualifier("kafkaTemplate")
    private KafkaTemplate<String, String> kafkaTemplate;

    private final String topic = "locations";
    private double lat = 40.712776;
    private double lon = -74.005974;
    private final Random random = new Random();

    /**
     * Periodically simulates location data and sends it to a Kafka topic.
     * Uses a Gaussian distribution to simulate small random drift from the current location, mimicking real-world GPS fluctuations.
     * The location data is serialized into JSON format before being sent.
     *
     * This method is scheduled to run at fixed rate intervals (every 5000 milliseconds by default).
     *
     * @throws JsonProcessingException if there is an error during the serialization of LocationData object to JSON string.
     */
    @Scheduled(fixedRate = 5000)
    public void simulateLocation() throws JsonProcessingException {
        lat += random.nextGaussian() * 0.01;
        lon += random.nextGaussian() * 0.01;

        LocationData data = new LocationData(lat, lon);
        ObjectMapper mapper = new ObjectMapper();
        kafkaTemplate.send(topic, mapper.writeValueAsString(data));

        log.info("Location sent -> Lat: {}, Lon: {}", lat, lon);
    }
}
