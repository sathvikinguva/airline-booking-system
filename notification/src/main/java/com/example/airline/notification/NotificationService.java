package com.example.airline.notification;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(serviceName = "NotificationService")
public class NotificationService {
    
    @WebMethod
    public boolean sendBookingConfirmation(@WebParam(name = "email") String email,
                                         @WebParam(name = "bookingId") String bookingId,
                                         @WebParam(name = "ticketNumber") String ticketNumber,
                                         @WebParam(name = "passengerName") String passengerName) {
        
        System.out.println("Sending booking confirmation to: " + email);
        System.out.println("Booking ID: " + bookingId + ", Ticket: " + ticketNumber);
        // Mock email sending
        return true;
    }
    
    @WebMethod
    public boolean sendSMS(@WebParam(name = "phone") String phone,
                          @WebParam(name = "message") String message) {
        System.out.println("Sending SMS to " + phone + ": " + message);
        return true;
    }
}