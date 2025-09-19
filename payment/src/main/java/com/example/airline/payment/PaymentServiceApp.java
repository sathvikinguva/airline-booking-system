package com.example.airline.payment;

import javax.xml.ws.Endpoint;

public class PaymentServiceApp {
    private static final String SERVICE_URL = "http://localhost:8084/payment";
    
    public static void main(String[] args) {
        try {
            PaymentService service = new PaymentService();
            Endpoint endpoint = Endpoint.publish(SERVICE_URL, service);
            
            System.out.println("=== Payment Service Started ===");
            System.out.println("Service URL: " + SERVICE_URL);
            System.out.println("WSDL URL: " + SERVICE_URL + "?wsdl");
            System.out.println("Service is running. Press Ctrl+C to stop.");
            System.out.println("===============================");
            
            while (endpoint.isPublished()) {
                Thread.sleep(1000);
            }
            
        } catch (Exception e) {
            System.err.println("Error starting Payment Service: " + e.getMessage());
            e.printStackTrace();
        }
    }
}