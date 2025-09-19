package com.example.airline.orchestrator;

import javax.xml.ws.Endpoint;

public class OrchestratorServiceApp {
    
    private static final String SERVICE_URL = "http://localhost:8090/orchestrator";
    
    public static void main(String[] args) {
        try {
            OrchestratorService service = new OrchestratorService();
            Endpoint endpoint = Endpoint.publish(SERVICE_URL, service);
            
            System.out.println("=== Orchestrator Service Started ===");
            System.out.println("Service URL: " + SERVICE_URL);
            System.out.println("WSDL URL: " + SERVICE_URL + "?wsdl");
            System.out.println("This is the main booking service that coordinates all operations.");
            System.out.println("Make sure other services are running before using this service.");
            System.out.println("Service is running. Press Ctrl+C to stop.");
            System.out.println("=====================================");
            
            // Keep the service running
            while (endpoint.isPublished()) {
                Thread.sleep(1000);
            }
            
        } catch (Exception e) {
            System.err.println("Error starting Orchestrator Service: " + e.getMessage());
            e.printStackTrace();
        }
    }
}