package com.example.airline.seatavailability;

import com.example.airline.models.Seat;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@WebService(serviceName = "SeatAvailabilityService")
public class SeatAvailabilityService {
    
    // Mock seat database with concurrent access support
    private static final Map<String, List<Seat>> SEAT_DATABASE = new ConcurrentHashMap<>();
    
    // Initialize mock data
    static {
        initializeSeatData();
    }
    
    private static void initializeSeatData() {
        // Initialize seats for different flights
        String[] flightIds = {"FL001", "FL002", "FL003", "FL004", "FL005", "FL006", "FL007", "FL008", "FL009", "FL010"};
        
        for (String flightId : flightIds) {
            List<Seat> seats = new ArrayList<>();
            
            // Create economy seats (rows 1-20, 6 seats per row)
            for (int row = 1; row <= 20; row++) {
                String[] seatLetters = {"A", "B", "C", "D", "E", "F"};
                String[] seatTypes = {"WINDOW", "MIDDLE", "AISLE", "AISLE", "MIDDLE", "WINDOW"};
                
                for (int i = 0; i < seatLetters.length; i++) {
                    String seatNumber = row + seatLetters[i];
                    String seatId = flightId + "-" + seatNumber;
                    boolean isAvailable = Math.random() > 0.3; // 70% seats available
                    
                    Seat seat = new Seat(seatId, flightId, seatNumber, seatTypes[i], "ECONOMY", isAvailable);
                    seats.add(seat);
                }
            }
            
            // Create business seats (rows 21-25, 4 seats per row)
            for (int row = 21; row <= 25; row++) {
                String[] seatLetters = {"A", "C", "D", "F"};
                String[] seatTypes = {"WINDOW", "AISLE", "AISLE", "WINDOW"};
                
                for (int i = 0; i < seatLetters.length; i++) {
                    String seatNumber = row + seatLetters[i];
                    String seatId = flightId + "-" + seatNumber;
                    boolean isAvailable = Math.random() > 0.4; // 60% business seats available
                    
                    Seat seat = new Seat(seatId, flightId, seatNumber, seatTypes[i], "BUSINESS", isAvailable);
                    seat.setExtraFee(1500.0); // Business class extra fee
                    seats.add(seat);
                }
            }
            
            SEAT_DATABASE.put(flightId, seats);
        }
    }

    @WebMethod
    public List<Seat> getAvailableSeats(@WebParam(name = "flightId") String flightId) {
        System.out.println("Getting available seats for flight: " + flightId);
        
        List<Seat> seats = SEAT_DATABASE.get(flightId);
        if (seats == null) {
            return new ArrayList<>();
        }
        
        return seats.stream()
                .filter(seat -> seat.isAvailable() && !seat.isBlocked())
                .collect(Collectors.toList());
    }

    @WebMethod
    public List<Seat> getAvailableSeatsByType(@WebParam(name = "flightId") String flightId,
                                            @WebParam(name = "seatType") String seatType) {
        System.out.println("Getting available " + seatType + " seats for flight: " + flightId);
        
        List<Seat> seats = SEAT_DATABASE.get(flightId);
        if (seats == null) {
            return new ArrayList<>();
        }
        
        return seats.stream()
                .filter(seat -> seat.isAvailable() && 
                               !seat.isBlocked() && 
                               seat.getSeatType().equalsIgnoreCase(seatType))
                .collect(Collectors.toList());
    }

    @WebMethod
    public List<Seat> getAvailableSeatsByClass(@WebParam(name = "flightId") String flightId,
                                             @WebParam(name = "seatClass") String seatClass) {
        System.out.println("Getting available " + seatClass + " class seats for flight: " + flightId);
        
        List<Seat> seats = SEAT_DATABASE.get(flightId);
        if (seats == null) {
            return new ArrayList<>();
        }
        
        return seats.stream()
                .filter(seat -> seat.isAvailable() && 
                               !seat.isBlocked() && 
                               seat.getSeatClass().equalsIgnoreCase(seatClass))
                .collect(Collectors.toList());
    }

    @WebMethod
    public int getAvailableSeatCount(@WebParam(name = "flightId") String flightId) {
        System.out.println("Getting available seat count for flight: " + flightId);
        
        List<Seat> seats = SEAT_DATABASE.get(flightId);
        if (seats == null) {
            return 0;
        }
        
        return (int) seats.stream()
                .filter(seat -> seat.isAvailable() && !seat.isBlocked())
                .count();
    }

    @WebMethod
    public boolean blockSeat(@WebParam(name = "flightId") String flightId,
                           @WebParam(name = "seatNumber") String seatNumber,
                           @WebParam(name = "userId") String userId,
                           @WebParam(name = "blockMinutes") int blockMinutes) {
        
        System.out.println("Blocking seat " + seatNumber + " for user " + userId + " for " + blockMinutes + " minutes");
        
        List<Seat> seats = SEAT_DATABASE.get(flightId);
        if (seats == null) {
            return false;
        }
        
        Optional<Seat> seatOpt = seats.stream()
                .filter(seat -> seat.getSeatNumber().equals(seatNumber))
                .findFirst();
        
        if (seatOpt.isPresent()) {
            Seat seat = seatOpt.get();
            if (seat.isAvailable() && !seat.isBlocked()) {
                seat.setBlocked(true);
                seat.setBlockedBy(userId);
                seat.setBlockExpiryTime(System.currentTimeMillis() + (blockMinutes * 60 * 1000));
                return true;
            }
        }
        
        return false;
    }

    @WebMethod
    public boolean releaseSeat(@WebParam(name = "flightId") String flightId,
                             @WebParam(name = "seatNumber") String seatNumber,
                             @WebParam(name = "userId") String userId) {
        
        System.out.println("Releasing seat " + seatNumber + " for user " + userId);
        
        List<Seat> seats = SEAT_DATABASE.get(flightId);
        if (seats == null) {
            return false;
        }
        
        Optional<Seat> seatOpt = seats.stream()
                .filter(seat -> seat.getSeatNumber().equals(seatNumber))
                .findFirst();
        
        if (seatOpt.isPresent()) {
            Seat seat = seatOpt.get();
            if (seat.isBlocked() && userId.equals(seat.getBlockedBy())) {
                seat.setBlocked(false);
                seat.setBlockedBy(null);
                seat.setBlockExpiryTime(0);
                return true;
            }
        }
        
        return false;
    }

    @WebMethod
    public boolean reserveSeat(@WebParam(name = "flightId") String flightId,
                             @WebParam(name = "seatNumber") String seatNumber,
                             @WebParam(name = "userId") String userId) {
        
        System.out.println("Reserving seat " + seatNumber + " for user " + userId);
        
        List<Seat> seats = SEAT_DATABASE.get(flightId);
        if (seats == null) {
            return false;
        }
        
        Optional<Seat> seatOpt = seats.stream()
                .filter(seat -> seat.getSeatNumber().equals(seatNumber))
                .findFirst();
        
        if (seatOpt.isPresent()) {
            Seat seat = seatOpt.get();
            if (seat.isAvailable() && 
                (!seat.isBlocked() || userId.equals(seat.getBlockedBy()))) {
                seat.setAvailable(false);
                seat.setBlocked(false);
                seat.setBlockedBy(null);
                seat.setBlockExpiryTime(0);
                return true;
            }
        }
        
        return false;
    }

    @WebMethod
    public void cleanupExpiredBlocks(@WebParam(name = "flightId") String flightId) {
        System.out.println("Cleaning up expired blocks for flight: " + flightId);
        
        List<Seat> seats = SEAT_DATABASE.get(flightId);
        if (seats == null) {
            return;
        }
        
        seats.stream()
                .filter(Seat::isExpiredBlock)
                .forEach(seat -> {
                    seat.setBlocked(false);
                    seat.setBlockedBy(null);
                    seat.setBlockExpiryTime(0);
                    System.out.println("Released expired block on seat: " + seat.getSeatNumber());
                });
    }

    @WebMethod
    public Map<String, Integer> getSeatSummary(@WebParam(name = "flightId") String flightId) {
        System.out.println("Getting seat summary for flight: " + flightId);
        
        List<Seat> seats = SEAT_DATABASE.get(flightId);
        if (seats == null) {
            return new HashMap<>();
        }
        
        Map<String, Integer> summary = new HashMap<>();
        summary.put("total", seats.size());
        summary.put("available", (int) seats.stream().filter(seat -> seat.isAvailable() && !seat.isBlocked()).count());
        summary.put("blocked", (int) seats.stream().filter(Seat::isBlocked).count());
        summary.put("reserved", (int) seats.stream().filter(seat -> !seat.isAvailable()).count());
        summary.put("economy_available", (int) seats.stream()
                .filter(seat -> seat.isAvailable() && !seat.isBlocked() && "ECONOMY".equals(seat.getSeatClass()))
                .count());
        summary.put("business_available", (int) seats.stream()
                .filter(seat -> seat.isAvailable() && !seat.isBlocked() && "BUSINESS".equals(seat.getSeatClass()))
                .count());
        
        return summary;
    }
}