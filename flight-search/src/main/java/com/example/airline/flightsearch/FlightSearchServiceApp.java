package com.example.airline.flightsearch;

import javax.xml.ws.Endpoint;

public class FlightSearchServiceApp {
    
    private static final String SERVICE_URL = "http://localhost:8081/flightsearch";
    
    public static void main(String[] args) {
        try {
            // Create and publish the web service
            FlightSearchService service = new FlightSearchService();
            Endpoint endpoint = Endpoint.publish(SERVICE_URL, service);
            
            System.out.println("=== Flight Search Service Started ===");
            System.out.println("Service URL: " + SERVICE_URL);
            System.out.println("WSDL URL: " + SERVICE_URL + "?wsdl");
            System.out.println("Service is running. Press Ctrl+C to stop.");
            System.out.println("=========================================");
            
            // Keep the service running
            while (endpoint.isPublished()) {
                Thread.sleep(1000);
            }
            
        } catch (Exception e) {
            System.err.println("Error starting Flight Search Service: " + e.getMessage());
            e.printStackTrace();
        }
    }
}