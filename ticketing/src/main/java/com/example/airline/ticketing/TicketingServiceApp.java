package com.example.airline.ticketing;

import javax.xml.ws.Endpoint;

public class TicketingServiceApp {
    private static final String SERVICE_URL = "http://localhost:8086/ticketing";
    
    public static void main(String[] args) {
        try {
            TicketingService service = new TicketingService();
            Endpoint.publish(SERVICE_URL, service);
            
            System.out.println("=== Ticketing Service Started ===");
            System.out.println("Service URL: " + SERVICE_URL);
            System.out.println("==================================");
            
            while (true) Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}