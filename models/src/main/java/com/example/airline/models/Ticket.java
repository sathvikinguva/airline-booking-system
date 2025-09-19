package com.example.airline.models;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@XmlRootElement
public class Ticket implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String ticketId;
    private String bookingId;
    private String ticketNumber;
    private String passengerName;
    private Flight flight;
    private List<String> seatNumbers;
    private double totalAmount;
    private String ticketStatus; // CONFIRMED, CANCELLED, CHECKED_IN
    private String issueTimestamp;
    private String checkInTimestamp;
    private String boardingGate;
    private String boardingTime;
    private String specialServices;

    public Ticket() {}

    public Ticket(String ticketId, String bookingId, String ticketNumber, 
                  String passengerName, Flight flight, List<String> seatNumbers, 
                  double totalAmount) {
        this.ticketId = ticketId;
        this.bookingId = bookingId;
        this.ticketNumber = ticketNumber;
        this.passengerName = passengerName;
        this.flight = flight;
        this.seatNumbers = seatNumbers;
        this.totalAmount = totalAmount;
        this.ticketStatus = "CONFIRMED";
        this.issueTimestamp = String.valueOf(System.currentTimeMillis());
    }

    // Getters and Setters
    public String getTicketId() { return ticketId; }
    public void setTicketId(String ticketId) { this.ticketId = ticketId; }

    public String getBookingId() { return bookingId; }
    public void setBookingId(String bookingId) { this.bookingId = bookingId; }

    public String getTicketNumber() { return ticketNumber; }
    public void setTicketNumber(String ticketNumber) { this.ticketNumber = ticketNumber; }

    public String getPassengerName() { return passengerName; }
    public void setPassengerName(String passengerName) { this.passengerName = passengerName; }

    public Flight getFlight() { return flight; }
    public void setFlight(Flight flight) { this.flight = flight; }

    public List<String> getSeatNumbers() { return seatNumbers; }
    public void setSeatNumbers(List<String> seatNumbers) { this.seatNumbers = seatNumbers; }

    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }

    public String getTicketStatus() { return ticketStatus; }
    public void setTicketStatus(String ticketStatus) { this.ticketStatus = ticketStatus; }

    public String getIssueTimestamp() { return issueTimestamp; }
    public void setIssueTimestamp(String issueTimestamp) { this.issueTimestamp = issueTimestamp; }

    public String getCheckInTimestamp() { return checkInTimestamp; }
    public void setCheckInTimestamp(String checkInTimestamp) { this.checkInTimestamp = checkInTimestamp; }

    public String getBoardingGate() { return boardingGate; }
    public void setBoardingGate(String boardingGate) { this.boardingGate = boardingGate; }

    public String getBoardingTime() { return boardingTime; }
    public void setBoardingTime(String boardingTime) { this.boardingTime = boardingTime; }

    public String getSpecialServices() { return specialServices; }
    public void setSpecialServices(String specialServices) { this.specialServices = specialServices; }

    @Override
    public String toString() {
        return "Ticket{" +
                "ticketId='" + ticketId + '\'' +
                ", ticketNumber='" + ticketNumber + '\'' +
                ", passengerName='" + passengerName + '\'' +
                ", flight=" + flight.getFlightNumber() +
                ", seatNumbers=" + seatNumbers +
                ", totalAmount=" + totalAmount +
                ", ticketStatus='" + ticketStatus + '\'' +
                '}';
    }
}