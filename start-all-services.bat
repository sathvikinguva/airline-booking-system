@echo off
echo === Starting Airline Booking System Services ===
echo.
echo This script will start all services. 
echo Press Ctrl+C in any terminal to stop a service.
echo.
pause

echo Starting Flight Search Service...
start "Flight Search" cmd /k "cd flight-search && mvn exec:java -Dexec.mainClass=com.example.airline.flightsearch.FlightSearchServiceApp"
timeout /t 3

echo Starting Seat Availability Service...
start "Seat Availability" cmd /k "cd seat-availability && mvn exec:java -Dexec.mainClass=com.example.airline.seatavailability.SeatAvailabilityServiceApp"
timeout /t 3

echo Starting Authentication Service...
start "Authentication" cmd /k "cd auth && mvn exec:java -Dexec.mainClass=com.example.airline.auth.AuthServiceApp"
timeout /t 3

echo Starting Payment Service...
start "Payment" cmd /k "cd payment && mvn exec:java -Dexec.mainClass=com.example.airline.payment.PaymentServiceApp"
timeout /t 3

echo Starting Seat Allocation Service...
start "Seat Allocation" cmd /k "cd seat-allocation && mvn exec:java -Dexec.mainClass=com.example.airline.seatallocation.SeatAllocationServiceApp"
timeout /t 3

echo Starting Ticketing Service...
start "Ticketing" cmd /k "cd ticketing && mvn exec:java -Dexec.mainClass=com.example.airline.ticketing.TicketingServiceApp"
timeout /t 3

echo Starting Notification Service...
start "Notification" cmd /k "cd notification && mvn exec:java -Dexec.mainClass=com.example.airline.notification.NotificationServiceApp"
timeout /t 3

echo Starting Orchestrator Service...
start "Orchestrator" cmd /k "cd orchestrator && mvn exec:java -Dexec.mainClass=com.example.airline.orchestrator.OrchestratorServiceApp"
timeout /t 5

echo.
echo === All services are starting up ===
echo Wait for all services to show "Service is running" message
echo Then you can run the client: start-client.bat
echo.
pause