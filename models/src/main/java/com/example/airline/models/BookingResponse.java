package com.example.airline.models;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@XmlRootElement
public class BookingResponse implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private boolean success;
    private String bookingId;
    private String message;
    private double totalAmount;
    private String paymentStatus;
    private List<String> allocatedSeats;
    private String ticketNumber;
    private String confirmationCode;
    private Flight flight;
    private String bookingTimestamp;

    public BookingResponse() {}

    public BookingResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public BookingResponse(boolean success, String bookingId, String message, 
                          double totalAmount, String paymentStatus, 
                          List<String> allocatedSeats, String ticketNumber, 
                          String confirmationCode) {
        this.success = success;
        this.bookingId = bookingId;
        this.message = message;
        this.totalAmount = totalAmount;
        this.paymentStatus = paymentStatus;
        this.allocatedSeats = allocatedSeats;
        this.ticketNumber = ticketNumber;
        this.confirmationCode = confirmationCode;
    }

    // Getters and Setters
    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }

    public String getBookingId() { return bookingId; }
    public void setBookingId(String bookingId) { this.bookingId = bookingId; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }

    public String getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }

    public List<String> getAllocatedSeats() { return allocatedSeats; }
    public void setAllocatedSeats(List<String> allocatedSeats) { this.allocatedSeats = allocatedSeats; }

    public String getTicketNumber() { return ticketNumber; }
    public void setTicketNumber(String ticketNumber) { this.ticketNumber = ticketNumber; }

    public String getConfirmationCode() { return confirmationCode; }
    public void setConfirmationCode(String confirmationCode) { this.confirmationCode = confirmationCode; }

    public Flight getFlight() { return flight; }
    public void setFlight(Flight flight) { this.flight = flight; }

    public String getBookingTimestamp() { return bookingTimestamp; }
    public void setBookingTimestamp(String bookingTimestamp) { this.bookingTimestamp = bookingTimestamp; }

    @Override
    public String toString() {
        return "BookingResponse{" +
                "success=" + success +
                ", bookingId='" + bookingId + '\'' +
                ", message='" + message + '\'' +
                ", totalAmount=" + totalAmount +
                ", ticketNumber='" + ticketNumber + '\'' +
                ", confirmationCode='" + confirmationCode + '\'' +
                '}';
    }
}