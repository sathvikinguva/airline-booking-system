package com.example.airline.orchestrator;

import com.example.airline.models.*;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@WebService(serviceName = "OrchestratorService")
public class OrchestratorService {
    
    // Service URLs
    private static final String FLIGHT_SEARCH_URL = "http://localhost:8081/flightsearch?wsdl";
    private static final String SEAT_AVAILABILITY_URL = "http://localhost:8082/seatavailability?wsdl";
    private static final String AUTH_URL = "http://localhost:8083/auth?wsdl";
    private static final String PAYMENT_URL = "http://localhost:8084/payment?wsdl";
    private static final String SEAT_ALLOCATION_URL = "http://localhost:8085/seatallocation?wsdl";
    private static final String TICKETING_URL = "http://localhost:8086/ticketing?wsdl";
    private static final String NOTIFICATION_URL = "http://localhost:8087/notification?wsdl";

    @WebMethod
    public BookingResponse bookTicket(@WebParam(name = "authToken") String authToken,
                                     @WebParam(name = "bookingRequest") BookingRequest bookingRequest) {
        
        System.out.println("=== Starting Booking Process ===");
        System.out.println("Booking request: " + bookingRequest);
        
        try {
            // Step 1: Validate authentication token
            System.out.println("Step 1: Validating authentication...");
            if (!validateAuth(authToken)) {
                return new BookingResponse(false, "Authentication failed");
            }
            
            // Step 2: Get flight details
            System.out.println("Step 2: Getting flight details...");
            Flight flight = getFlightDetails(bookingRequest.getFlightId());
            if (flight == null) {
                return new BookingResponse(false, "Flight not found");
            }
            
            // Step 3: Check seat availability
            System.out.println("Step 3: Checking seat availability...");
            if (!checkSeatAvailability(bookingRequest.getFlightId(), bookingRequest.getNumberOfSeats())) {
                return new BookingResponse(false, "Insufficient seats available");
            }
            
            // Step 4: Allocate seats
            System.out.println("Step 4: Allocating seats...");
            List<String> allocatedSeats = allocateSeats(bookingRequest);
            if (allocatedSeats == null || allocatedSeats.isEmpty()) {
                return new BookingResponse(false, "Seat allocation failed");
            }
            
            // Step 5: Process payment
            System.out.println("Step 5: Processing payment...");
            double totalAmount = flight.getPrice() * bookingRequest.getNumberOfSeats();
            boolean paymentSuccess = processPayment(bookingRequest.getPaymentDetails(), totalAmount);
            if (!paymentSuccess) {
                // Rollback seat allocation
                deallocateSeats(bookingRequest.getFlightId(), allocatedSeats);
                return new BookingResponse(false, "Payment processing failed");
            }
            
            // Step 6: Generate ticket
            System.out.println("Step 6: Generating ticket...");
            Ticket ticket = generateTicket(bookingRequest, flight, allocatedSeats, totalAmount);
            if (ticket == null) {
                return new BookingResponse(false, "Ticket generation failed");
            }
            
            // Step 7: Send notification
            System.out.println("Step 7: Sending confirmation...");
            sendNotification(bookingRequest.getPassengerEmail(), ticket);
            
            // Step 8: Create successful response
            BookingResponse response = new BookingResponse(
                true,
                "BOOKING_" + System.currentTimeMillis(),
                "Booking successful! Your ticket number is " + ticket.getTicketNumber(),
                totalAmount,
                "COMPLETED",
                allocatedSeats,
                ticket.getTicketNumber(),
                "CONF" + new Random().nextInt(100000)
            );
            
            response.setFlight(flight);
            response.setBookingTimestamp(String.valueOf(System.currentTimeMillis()));
            
            System.out.println("=== Booking Process Completed Successfully ===");
            return response;
            
        } catch (Exception e) {
            System.err.println("Error during booking process: " + e.getMessage());
            e.printStackTrace();
            return new BookingResponse(false, "Booking failed due to system error: " + e.getMessage());
        }
    }

    @WebMethod
    public List<Flight> searchFlights(@WebParam(name = "source") String source,
                                    @WebParam(name = "destination") String destination,
                                    @WebParam(name = "date") String date) {
        
        System.out.println("Orchestrator: Searching flights from " + source + " to " + destination);
        
        try {
            // Mock flight search response
            Flight mockFlight = new Flight("FL001", "AI101", "Air India", source, destination, 
                                          "06:00", "08:30", date, 5500.0, 150, 45, "Boeing 737");
            return Arrays.asList(mockFlight);
            
        } catch (Exception e) {
            System.err.println("Error searching flights: " + e.getMessage());
            return Arrays.asList();
        }
    }
    
    // Helper methods with mock implementations
    private boolean validateAuth(String token) {
        // Mock auth validation
        return token != null && token.startsWith("TOKEN_");
    }
    
    private Flight getFlightDetails(String flightId) {
        // Mock flight details
        return new Flight(flightId, "AI101", "Air India", "Delhi", "Mumbai", 
                         "06:00", "08:30", "2025-09-20", 5500.0, 150, 45, "Boeing 737");
    }
    
    private boolean checkSeatAvailability(String flightId, int requiredSeats) {
        // Mock availability check
        return requiredSeats <= 10; // Assume max 10 seats can be booked at once
    }
    
    private List<String> allocateSeats(BookingRequest request) {
        // Mock seat allocation
        return Arrays.asList("1A", "1B");
    }
    
    private boolean processPayment(PaymentDetails paymentDetails, double amount) {
        // Mock payment processing
        System.out.println("Processing payment of $" + amount);
        return paymentDetails != null && amount > 0;
    }
    
    private void deallocateSeats(String flightId, List<String> seats) {
        System.out.println("Deallocating seats: " + seats + " for flight: " + flightId);
    }
    
    private Ticket generateTicket(BookingRequest request, Flight flight, 
                                List<String> seats, double amount) {
        
        String ticketId = "TICKET_" + System.currentTimeMillis();
        String ticketNumber = "TK" + new Random().nextInt(900000) + 100000;
        
        return new Ticket(ticketId, "BOOKING_" + System.currentTimeMillis(), 
                         ticketNumber, request.getPassengerName(), 
                         flight, seats, amount);
    }
    
    private void sendNotification(String email, Ticket ticket) {
        System.out.println("Sending confirmation email to: " + email + 
                          " for ticket: " + ticket.getTicketNumber());
    }
}