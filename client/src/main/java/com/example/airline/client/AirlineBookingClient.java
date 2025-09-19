package com.example.airline.client;

import com.example.airline.models.*;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class AirlineBookingClient {
    
    private static final String ORCHESTRATOR_URL = "http://localhost:8090/orchestrator?wsdl";
    private static final String AUTH_URL = "http://localhost:8083/auth?wsdl";
    private static final Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        System.out.println("=== Welcome to Airline Booking System ===");
        System.out.println("Make sure all services are running before proceeding!");
        System.out.println("==========================================");
        
        try {
            // Step 1: Authentication
            String authToken = authenticateUser();
            if (authToken == null) {
                System.out.println("Authentication failed. Exiting...");
                return;
            }
            
            // Step 2: Search flights (optional demo)
            demonstrateFlightSearch();
            
            // Step 3: Create booking request
            BookingRequest bookingRequest = createBookingRequest();
            if (bookingRequest == null) {
                System.out.println("Invalid booking request. Exiting...");
                return;
            }
            
            // Step 4: Process booking
            BookingResponse response = processBooking(authToken, bookingRequest);
            
            // Step 5: Display result
            displayBookingResult(response);
            
        } catch (Exception e) {
            System.err.println("Error in client application: " + e.getMessage());
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
    
    private static String authenticateUser() {
        System.out.println("\n=== User Authentication ===");
        System.out.println("Demo users available:");
        System.out.println("- Username: john_doe, Password: password123");
        System.out.println("- Username: jane_smith, Password: password456");
        System.out.println("- Username: bob_wilson, Password: password789");
        
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        
        // Mock authentication - in real implementation, call auth service
        if ("john_doe".equals(username) && "password123".equals(password)) {
            String token = "TOKEN_user001_" + System.currentTimeMillis() + "_1234";
            System.out.println("Authentication successful! Token: " + token);
            return token;
        } else if ("jane_smith".equals(username) && "password456".equals(password)) {
            String token = "TOKEN_user002_" + System.currentTimeMillis() + "_5678";
            System.out.println("Authentication successful! Token: " + token);
            return token;
        } else if ("bob_wilson".equals(username) && "password789".equals(password)) {
            String token = "TOKEN_user003_" + System.currentTimeMillis() + "_9012";
            System.out.println("Authentication successful! Token: " + token);
            return token;
        } else {
            System.out.println("Invalid username or password!");
            return null;
        }
    }
    
    private static void demonstrateFlightSearch() {
        System.out.println("\n=== Flight Search Demo ===");
        System.out.println("Available routes:");
        System.out.println("- Delhi to Mumbai");
        System.out.println("- Mumbai to Delhi");
        System.out.println("- Delhi to Bangalore");
        System.out.println("- Bangalore to Delhi");
        System.out.println("- Delhi to Chennai");
        System.out.println("- Chennai to Delhi");
        System.out.println("==========================");
    }
    
    private static BookingRequest createBookingRequest() {
        System.out.println("\n=== Create Booking Request ===");
        
        System.out.print("Enter flight ID (e.g., FL001): ");
        String flightId = scanner.nextLine();
        
        System.out.print("Enter number of seats (1-4): ");
        int numberOfSeats;
        try {
            numberOfSeats = Integer.parseInt(scanner.nextLine());
            if (numberOfSeats < 1 || numberOfSeats > 4) {
                System.out.println("Invalid number of seats!");
                return null;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format!");
            return null;
        }
        
        System.out.print("Enter passenger name: ");
        String passengerName = scanner.nextLine();
        
        System.out.print("Enter passenger email: ");
        String passengerEmail = scanner.nextLine();
        
        System.out.print("Enter passenger phone: ");
        String passengerPhone = scanner.nextLine();
        
        System.out.print("Enter seat preference (WINDOW/AISLE/MIDDLE): ");
        String seatPreference = scanner.nextLine().toUpperCase();
        if (!Arrays.asList("WINDOW", "AISLE", "MIDDLE").contains(seatPreference)) {
            seatPreference = "WINDOW"; // Default
        }
        
        // Create payment details
        PaymentDetails paymentDetails = createPaymentDetails();
        if (paymentDetails == null) {
            return null;
        }
        
        // Create booking request
        BookingRequest request = new BookingRequest("user001", flightId, numberOfSeats, 
                                                   seatPreference, passengerName, 
                                                   passengerEmail, passengerPhone);
        request.setPaymentDetails(paymentDetails);
        
        return request;
    }
    
    private static PaymentDetails createPaymentDetails() {
        System.out.println("\n--- Payment Details ---");
        
        System.out.print("Enter card number (16 digits): ");
        String cardNumber = scanner.nextLine();
        
        System.out.print("Enter card holder name: ");
        String cardHolderName = scanner.nextLine();
        
        System.out.print("Enter expiry month (MM): ");
        String expiryMonth = scanner.nextLine();
        
        System.out.print("Enter expiry year (YYYY): ");
        String expiryYear = scanner.nextLine();
        
        System.out.print("Enter CVV: ");
        String cvv = scanner.nextLine();
        
        System.out.print("Enter card type (VISA/MASTERCARD/AMEX): ");
        String cardType = scanner.nextLine().toUpperCase();
        
        return new PaymentDetails(cardNumber, cardHolderName, expiryMonth, 
                                expiryYear, cvv, cardType);
    }
    
    private static BookingResponse processBooking(String authToken, BookingRequest request) {
        System.out.println("\n=== Processing Booking ===");
        System.out.println("Contacting orchestrator service...");
        
        try {
            // In a real implementation, you would call the actual SOAP service
            // For demo purposes, we'll create a mock successful response
            
            BookingResponse response = new BookingResponse(
                true,
                "BOOKING_" + System.currentTimeMillis(),
                "Booking successful! Your flight has been confirmed.",
                5500.0 * request.getNumberOfSeats(),
                "COMPLETED",
                Arrays.asList("1A", "1B"),
                "TK" + (100000 + (int)(Math.random() * 900000)),
                "CONF" + (10000 + (int)(Math.random() * 90000))
            );
            
            Flight flight = new Flight("FL001", "AI101", "Air India", "Delhi", "Mumbai",
                                      "06:00", "08:30", "2025-09-20", 5500.0, 150, 45, "Boeing 737");
            response.setFlight(flight);
            response.setBookingTimestamp(String.valueOf(System.currentTimeMillis()));
            
            return response;
            
        } catch (Exception e) {
            System.err.println("Error processing booking: " + e.getMessage());
            return new BookingResponse(false, "Booking failed: " + e.getMessage());
        }
    }
    
    private static void displayBookingResult(BookingResponse response) {
        System.out.println("\n=== Booking Result ===");
        
        if (response.isSuccess()) {
            System.out.println("✅ BOOKING SUCCESSFUL!");
            System.out.println("Booking ID: " + response.getBookingId());
            System.out.println("Ticket Number: " + response.getTicketNumber());
            System.out.println("Confirmation Code: " + response.getConfirmationCode());
            System.out.println("Total Amount: $" + response.getTotalAmount());
            System.out.println("Allocated Seats: " + response.getAllocatedSeats());
            System.out.println("Payment Status: " + response.getPaymentStatus());
            
            if (response.getFlight() != null) {
                Flight flight = response.getFlight();
                System.out.println("\nFlight Details:");
                System.out.println("Flight Number: " + flight.getFlightNumber());
                System.out.println("Airline: " + flight.getAirline());
                System.out.println("Route: " + flight.getSource() + " → " + flight.getDestination());
                System.out.println("Date: " + flight.getDate());
                System.out.println("Departure: " + flight.getDepartureTime());
                System.out.println("Arrival: " + flight.getArrivalTime());
                System.out.println("Aircraft: " + flight.getAircraftType());
            }
            
            System.out.println("\n🎉 Thank you for choosing our airline!");
            System.out.println("A confirmation email has been sent to your registered email address.");
            
        } else {
            System.out.println("❌ BOOKING FAILED!");
            System.out.println("Reason: " + response.getMessage());
            System.out.println("Please try again or contact customer support.");
        }
        
        System.out.println("======================");
    }
}