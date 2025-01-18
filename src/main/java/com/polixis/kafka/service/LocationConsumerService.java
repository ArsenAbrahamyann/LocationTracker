package com.polixis.kafka.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import com.polixis.kafka.model.LocationData;

/**
 * Service class that consumes location data messages from a Kafka topic.
 * This class listens for messages on the 'locations' topic, processes those messages to extract location data,
 * and calculates the distance moved from the last known point. This service is designed to track the total distance
 * covered based on the sequence of incoming location data.
 */
@Service
@Slf4j
public class LocationConsumerService {
    private LocationData previousLocation = null;
    private double totalDistance = 0.0;

    /**
     * Listens and processes location data messages from the Kafka topic named "locations".
     * For each message received, this method deserializes the JSON message into a LocationData object, calculates the
     * distance from the previous location to the current location using the Haversine formula, and updates the total
     * distance traveled.
     *
     * If there is an issue deserializing the message, the error is logged and the method exits without updating the location or distance.
     *
     * @param message JSON string representing serialized location data. Expected to be deserializable into a LocationData object.
     */
    @KafkaListener(topics = "locations", groupId = "location-tracker-group")
    public void listenLocationData(String message) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            LocationData currentLocation = mapper.readValue(message, LocationData.class);

            if (previousLocation != null) {
                double distance = HaversineUtil.calculateDistance(
                        previousLocation.getLatitude(), previousLocation.getLongitude(),
                        currentLocation.getLatitude(), currentLocation.getLongitude());

                totalDistance += distance;
                log.info(String.format("Distance between last two points: %.2f km", distance));
                log.info(String.format("Total Distance covered: %.2f km", totalDistance));
            }

            previousLocation = currentLocation;

        } catch (IOException e) {
            log.error("Error parsing message: " + message, e);
        }
    }
}
