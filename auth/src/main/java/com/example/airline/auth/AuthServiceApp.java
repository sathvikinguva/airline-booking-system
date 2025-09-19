package com.example.airline.auth;

import javax.xml.ws.Endpoint;

public class AuthServiceApp {
    
    private static final String SERVICE_URL = "http://localhost:8083/auth";
    
    public static void main(String[] args) {
        try {
            AuthService service = new AuthService();
            Endpoint endpoint = Endpoint.publish(SERVICE_URL, service);
            
            System.out.println("=== Authentication Service Started ===");
            System.out.println("Service URL: " + SERVICE_URL);
            System.out.println("WSDL URL: " + SERVICE_URL + "?wsdl");
            System.out.println("Service is running. Press Ctrl+C to stop.");
            System.out.println("======================================");
            
            while (endpoint.isPublished()) {
                Thread.sleep(1000);
            }
            
        } catch (Exception e) {
            System.err.println("Error starting Authentication Service: " + e.getMessage());
            e.printStackTrace();
        }
    }
}