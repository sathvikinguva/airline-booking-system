package com.example.airline.seatallocation;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.*;

@WebService(serviceName = "SeatAllocationService")
public class SeatAllocationService {
    
    @WebMethod
    public List<String> allocateSeats(@WebParam(name = "flightId") String flightId,
                                     @WebParam(name = "numberOfSeats") int numberOfSeats,
                                     @WebParam(name = "seatPreference") String seatPreference,
                                     @WebParam(name = "userId") String userId) {
        
        System.out.println("Allocating " + numberOfSeats + " seats for user " + userId + 
                          " on flight " + flightId + " with preference: " + seatPreference);
        
        List<String> allocatedSeats = new ArrayList<>();
        
        // Mock seat allocation logic
        String[] rows = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        String[] letters = {"A", "B", "C", "D", "E", "F"};
        
        Random random = new Random();
        Set<String> usedSeats = new HashSet<>();
        
        for (int i = 0; i < numberOfSeats; i++) {
            String seat;
            do {
                String row = rows[random.nextInt(rows.length)];
                String letter;
                
                if ("WINDOW".equalsIgnoreCase(seatPreference)) {
                    letter = random.nextBoolean() ? "A" : "F";
                } else if ("AISLE".equalsIgnoreCase(seatPreference)) {
                    letter = random.nextBoolean() ? "C" : "D";
                } else {
                    letter = letters[random.nextInt(letters.length)];
                }
                
                seat = row + letter;
            } while (usedSeats.contains(seat));
            
            usedSeats.add(seat);
            allocatedSeats.add(seat);
        }
        
        System.out.println("Allocated seats: " + allocatedSeats);
        return allocatedSeats;
    }
    
    @WebMethod
    public boolean deallocateSeats(@WebParam(name = "flightId") String flightId,
                                  @WebParam(name = "seatNumbers") List<String> seatNumbers,
                                  @WebParam(name = "userId") String userId) {
        
        System.out.println("Deallocating seats " + seatNumbers + " for user " + userId + " on flight " + flightId);
        // Mock deallocation (always succeeds)
        return true;
    }
}