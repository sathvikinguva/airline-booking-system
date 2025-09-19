package com.example.airline.models;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
public class Seat implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String seatId;
    private String flightId;
    private String seatNumber;
    private String seatType; // WINDOW, AISLE, MIDDLE
    private String seatClass; // ECONOMY, BUSINESS, FIRST
    private boolean isAvailable;
    private boolean isBlocked;
    private String blockedBy; // User ID who has temporarily blocked this seat
    private long blockExpiryTime;
    private double extraFee;

    public Seat() {}

    public Seat(String seatId, String flightId, String seatNumber, 
                String seatType, String seatClass, boolean isAvailable) {
        this.seatId = seatId;
        this.flightId = flightId;
        this.seatNumber = seatNumber;
        this.seatType = seatType;
        this.seatClass = seatClass;
        this.isAvailable = isAvailable;
        this.isBlocked = false;
        this.extraFee = 0.0;
    }

    // Getters and Setters
    public String getSeatId() { return seatId; }
    public void setSeatId(String seatId) { this.seatId = seatId; }

    public String getFlightId() { return flightId; }
    public void setFlightId(String flightId) { this.flightId = flightId; }

    public String getSeatNumber() { return seatNumber; }
    public void setSeatNumber(String seatNumber) { this.seatNumber = seatNumber; }

    public String getSeatType() { return seatType; }
    public void setSeatType(String seatType) { this.seatType = seatType; }

    public String getSeatClass() { return seatClass; }
    public void setSeatClass(String seatClass) { this.seatClass = seatClass; }

    public boolean isAvailable() { return isAvailable; }
    public void setAvailable(boolean available) { isAvailable = available; }

    public boolean isBlocked() { return isBlocked; }
    public void setBlocked(boolean blocked) { isBlocked = blocked; }

    public String getBlockedBy() { return blockedBy; }
    public void setBlockedBy(String blockedBy) { this.blockedBy = blockedBy; }

    public long getBlockExpiryTime() { return blockExpiryTime; }
    public void setBlockExpiryTime(long blockExpiryTime) { this.blockExpiryTime = blockExpiryTime; }

    public double getExtraFee() { return extraFee; }
    public void setExtraFee(double extraFee) { this.extraFee = extraFee; }

    public boolean isExpiredBlock() {
        return isBlocked && System.currentTimeMillis() > blockExpiryTime;
    }

    @Override
    public String toString() {
        return "Seat{" +
                "seatId='" + seatId + '\'' +
                ", seatNumber='" + seatNumber + '\'' +
                ", seatType='" + seatType + '\'' +
                ", seatClass='" + seatClass + '\'' +
                ", isAvailable=" + isAvailable +
                ", extraFee=" + extraFee +
                '}';
    }
}