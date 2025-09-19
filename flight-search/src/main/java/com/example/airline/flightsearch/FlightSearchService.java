package com.example.airline.flightsearch;

import com.example.airline.models.Flight;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@WebService(serviceName = "FlightSearchService")
public class FlightSearchService {
    
    // Mock flight database
    private static final List<Flight> FLIGHT_DATABASE = Arrays.asList(
        new Flight("FL001", "AI101", "Air India", "Delhi", "Mumbai", 
                   "06:00", "08:30", "2025-09-20", 5500.0, 150, 45, "Boeing 737"),
        new Flight("FL002", "AI102", "Air India", "Mumbai", "Delhi", 
                   "09:00", "11:30", "2025-09-20", 5500.0, 150, 67, "Boeing 737"),
        new Flight("FL003", "SG201", "SpiceJet", "Delhi", "Bangalore", 
                   "07:30", "10:00", "2025-09-20", 4200.0, 180, 32, "Airbus A320"),
        new Flight("FL004", "SG202", "SpiceJet", "Bangalore", "Delhi", 
                   "11:00", "13:30", "2025-09-20", 4200.0, 180, 78, "Airbus A320"),
        new Flight("FL005", "IG301", "IndiGo", "Delhi", "Chennai", 
                   "08:00", "10:45", "2025-09-20", 4800.0, 186, 23, "Airbus A320"),
        new Flight("FL006", "IG302", "IndiGo", "Chennai", "Delhi", 
                   "12:00", "14:45", "2025-09-20", 4800.0, 186, 89, "Airbus A320"),
        new Flight("FL007", "UK401", "Vistara", "Mumbai", "Bangalore", 
                   "14:30", "16:15", "2025-09-20", 3900.0, 158, 56, "Airbus A320"),
        new Flight("FL008", "UK402", "Vistara", "Bangalore", "Mumbai", 
                   "17:00", "18:45", "2025-09-20", 3900.0, 158, 34, "Airbus A320"),
        new Flight("FL009", "AI201", "Air India", "Delhi", "Kolkata", 
                   "09:15", "11:45", "2025-09-20", 5200.0, 150, 67, "Boeing 737"),
        new Flight("FL010", "AI202", "Air India", "Kolkata", "Delhi", 
                   "13:00", "15:30", "2025-09-20", 5200.0, 150, 23, "Boeing 737")
    );

    @WebMethod
    public List<Flight> searchFlights(@WebParam(name = "source") String source,
                                    @WebParam(name = "destination") String destination,
                                    @WebParam(name = "date") String date) {
        
        System.out.println("Searching flights from " + source + " to " + destination + " on " + date);
        
        return FLIGHT_DATABASE.stream()
                .filter(flight -> flight.getSource().equalsIgnoreCase(source) &&
                                flight.getDestination().equalsIgnoreCase(destination) &&
                                flight.getDate().equals(date) &&
                                flight.getAvailableSeats() > 0)
                .collect(Collectors.toList());
    }

    @WebMethod
    public Flight getFlightById(@WebParam(name = "flightId") String flightId) {
        System.out.println("Getting flight details for ID: " + flightId);
        
        return FLIGHT_DATABASE.stream()
                .filter(flight -> flight.getFlightId().equals(flightId))
                .findFirst()
                .orElse(null);
    }

    @WebMethod
    public List<Flight> getAllFlights() {
        System.out.println("Getting all available flights");
        return new ArrayList<>(FLIGHT_DATABASE);
    }

    @WebMethod
    public List<String> getAvailableRoutes() {
        System.out.println("Getting all available routes");
        
        return FLIGHT_DATABASE.stream()
                .map(flight -> flight.getSource() + " -> " + flight.getDestination())
                .distinct()
                .collect(Collectors.toList());
    }

    @WebMethod
    public List<Flight> searchFlightsByAirline(@WebParam(name = "airline") String airline,
                                             @WebParam(name = "date") String date) {
        System.out.println("Searching flights by airline: " + airline + " on " + date);
        
        return FLIGHT_DATABASE.stream()
                .filter(flight -> flight.getAirline().equalsIgnoreCase(airline) &&
                                flight.getDate().equals(date) &&
                                flight.getAvailableSeats() > 0)
                .collect(Collectors.toList());
    }

    @WebMethod
    public List<Flight> searchFlightsByPriceRange(@WebParam(name = "source") String source,
                                                @WebParam(name = "destination") String destination,
                                                @WebParam(name = "date") String date,
                                                @WebParam(name = "minPrice") double minPrice,
                                                @WebParam(name = "maxPrice") double maxPrice) {
        
        System.out.println("Searching flights by price range: " + minPrice + " - " + maxPrice);
        
        return FLIGHT_DATABASE.stream()
                .filter(flight -> flight.getSource().equalsIgnoreCase(source) &&
                                flight.getDestination().equalsIgnoreCase(destination) &&
                                flight.getDate().equals(date) &&
                                flight.getPrice() >= minPrice &&
                                flight.getPrice() <= maxPrice &&
                                flight.getAvailableSeats() > 0)
                .collect(Collectors.toList());
    }
}