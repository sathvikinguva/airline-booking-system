@echo off
echo === Starting Airline Booking Client ===
echo.
echo Make sure all services are running before proceeding!
echo.
echo Demo Users Available:
echo - Username: john_doe, Password: password123
echo - Username: jane_smith, Password: password456  
echo - Username: bob_wilson, Password: password789
echo.
pause

cd client
mvn exec:java -Dexec.mainClass=com.example.airline.client.AirlineBookingClient

pause