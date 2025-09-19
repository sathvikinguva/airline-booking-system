package com.example.airline.seatallocation;

import javax.xml.ws.Endpoint;

public class SeatAllocationServiceApp {
    private static final String SERVICE_URL = "http://localhost:8085/seatallocation";
    
    public static void main(String[] args) {
        try {
            SeatAllocationService service = new SeatAllocationService();
            Endpoint endpoint = Endpoint.publish(SERVICE_URL, service);
            
            System.out.println("=== Seat Allocation Service Started ===");
            System.out.println("Service URL: " + SERVICE_URL);
            System.out.println("WSDL URL: " + SERVICE_URL + "?wsdl");
            System.out.println("=======================================");
            
            while (endpoint.isPublished()) {
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            System.err.println("Error starting Seat Allocation Service: " + e.getMessage());
            e.printStackTrace();
        }
    }
}