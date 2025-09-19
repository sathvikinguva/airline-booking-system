package com.example.airline.ticketing;

import com.example.airline.models.*;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.List;
import java.util.Random;

@WebService(serviceName = "TicketingService")
public class TicketingService {
    
    @WebMethod
    public Ticket generateTicket(@WebParam(name = "bookingId") String bookingId,
                                @WebParam(name = "passengerName") String passengerName,
                                @WebParam(name = "flight") Flight flight,
                                @WebParam(name = "seatNumbers") List<String> seatNumbers,
                                @WebParam(name = "totalAmount") double totalAmount) {
        
        System.out.println("Generating ticket for booking: " + bookingId);
        
        String ticketId = "TICKET_" + System.currentTimeMillis();
        String ticketNumber = "TK" + new Random().nextInt(900000) + 100000;
        
        Ticket ticket = new Ticket(ticketId, bookingId, ticketNumber, passengerName, 
                                 flight, seatNumbers, totalAmount);
        
        System.out.println("Ticket generated: " + ticketNumber);
        return ticket;
    }
    
    @WebMethod
    public boolean cancelTicket(@WebParam(name = "ticketNumber") String ticketNumber) {
        System.out.println("Cancelling ticket: " + ticketNumber);
        return true; // Mock cancellation
    }
}