package com.polixis.kafka.service;

/**
 * Utility class providing a method to calculate the geodesic distance between two points on the earth.
 * The distance is calculated using the Haversine formula, which is an important equation used in navigation,
 * providing great-circle distances between two points on a sphere from their longitudes and latitudes.
 */
public class HaversineUtil {
    private static final double EARTH_RADIUS_KM = 6372.8;

    /**
     * Calculates the distance between two geographic points on the earth using the Haversine formula.
     * This method assumes that the Earth is a perfect sphere, and distances calculated are an approximation.
     *
     * @param startLat Latitude of the start point in degrees.
     * @param startLong Longitude of the start point in degrees.
     * @param endLat Latitude of the end point in degrees.
     * @param endLong Longitude of the end point in degrees.
     * @return The approximate distance between the two points in kilometers.
     */
    public static double calculateDistance(double startLat, double startLong,
                                           double endLat, double endLong) {
        double lat1 = Math.toRadians(startLat);
        double lon1 = Math.toRadians(startLong);
        double lat2 = Math.toRadians(endLat);
        double lon2 = Math.toRadians(endLong);

        double dLat = lat2 - lat1;
        double dLon = lon2 - lon1;

        double a = Math.pow(Math.sin(dLat / 2), 2) +
                Math.cos(lat1) * Math.cos(lat2) *
                        Math.pow(Math.sin(dLon / 2), 2);
        double c = 2 * Math.asin(Math.sqrt(a));

        return EARTH_RADIUS_KM * c;
    }
}
