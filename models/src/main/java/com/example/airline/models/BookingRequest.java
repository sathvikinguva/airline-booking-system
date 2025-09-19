package com.example.airline.models;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
public class BookingRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String userId;
    private String flightId;
    private int numberOfSeats;
    private String seatPreference; // WINDOW, AISLE, MIDDLE
    private String passengerName;
    private String passengerEmail;
    private String passengerPhone;
    private String specialRequests;
    private PaymentDetails paymentDetails;

    public BookingRequest() {}

    public BookingRequest(String userId, String flightId, int numberOfSeats, 
                         String seatPreference, String passengerName, 
                         String passengerEmail, String passengerPhone) {
        this.userId = userId;
        this.flightId = flightId;
        this.numberOfSeats = numberOfSeats;
        this.seatPreference = seatPreference;
        this.passengerName = passengerName;
        this.passengerEmail = passengerEmail;
        this.passengerPhone = passengerPhone;
    }

    // Getters and Setters
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getFlightId() { return flightId; }
    public void setFlightId(String flightId) { this.flightId = flightId; }

    public int getNumberOfSeats() { return numberOfSeats; }
    public void setNumberOfSeats(int numberOfSeats) { this.numberOfSeats = numberOfSeats; }

    public String getSeatPreference() { return seatPreference; }
    public void setSeatPreference(String seatPreference) { this.seatPreference = seatPreference; }

    public String getPassengerName() { return passengerName; }
    public void setPassengerName(String passengerName) { this.passengerName = passengerName; }

    public String getPassengerEmail() { return passengerEmail; }
    public void setPassengerEmail(String passengerEmail) { this.passengerEmail = passengerEmail; }

    public String getPassengerPhone() { return passengerPhone; }
    public void setPassengerPhone(String passengerPhone) { this.passengerPhone = passengerPhone; }

    public String getSpecialRequests() { return specialRequests; }
    public void setSpecialRequests(String specialRequests) { this.specialRequests = specialRequests; }

    public PaymentDetails getPaymentDetails() { return paymentDetails; }
    public void setPaymentDetails(PaymentDetails paymentDetails) { this.paymentDetails = paymentDetails; }

    @Override
    public String toString() {
        return "BookingRequest{" +
                "userId='" + userId + '\'' +
                ", flightId='" + flightId + '\'' +
                ", numberOfSeats=" + numberOfSeats +
                ", passengerName='" + passengerName + '\'' +
                ", passengerEmail='" + passengerEmail + '\'' +
                '}';
    }
}