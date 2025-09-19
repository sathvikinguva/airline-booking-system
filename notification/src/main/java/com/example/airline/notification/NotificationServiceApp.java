package com.example.airline.notification;

import javax.xml.ws.Endpoint;

public class NotificationServiceApp {
    private static final String SERVICE_URL = "http://localhost:8087/notification";
    
    public static void main(String[] args) {
        try {
            NotificationService service = new NotificationService();
            Endpoint.publish(SERVICE_URL, service);
            
            System.out.println("=== Notification Service Started ===");
            System.out.println("Service URL: " + SERVICE_URL);
            System.out.println("====================================");
            
            while (true) Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}