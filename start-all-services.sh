#!/bin/bash

echo "=== Starting Airline Booking System Services ==="
echo
echo "This script will start all services with Java 11+ compatibility."
echo "Press Ctrl+C in any terminal to stop a service."
echo

# Set Maven options for Java 11+ compatibility
export MAVEN_OPTS="--add-opens java.base/java.lang=ALL-UNNAMED --add-opens java.base/java.lang.reflect=ALL-UNNAMED --add-opens java.base/java.io=ALL-UNNAMED --add-opens java.base/java.security=ALL-UNNAMED --add-opens java.base/java.util=ALL-UNNAMED --add-opens java.base/java.lang.invoke=ALL-UNNAMED --add-opens java.base/sun.misc=ALL-UNNAMED"

echo "Starting services in background..."

# Start each service in background
cd flight-search && mvn exec:java > ../logs/flight-search.log 2>&1 &
FLIGHT_SEARCH_PID=$!
echo "Flight Search Service started (PID: $FLIGHT_SEARCH_PID)"
sleep 3

cd ../seat-availability && mvn exec:java > ../logs/seat-availability.log 2>&1 &
SEAT_AVAIL_PID=$!
echo "Seat Availability Service started (PID: $SEAT_AVAIL_PID)"
sleep 3

cd ../auth && mvn exec:java > ../logs/auth.log 2>&1 &
AUTH_PID=$!
echo "Authentication Service started (PID: $AUTH_PID)"
sleep 3

cd ../payment && mvn exec:java > ../logs/payment.log 2>&1 &
PAYMENT_PID=$!
echo "Payment Service started (PID: $PAYMENT_PID)"
sleep 3

cd ../seat-allocation && mvn exec:java > ../logs/seat-allocation.log 2>&1 &
SEAT_ALLOC_PID=$!
echo "Seat Allocation Service started (PID: $SEAT_ALLOC_PID)"
sleep 3

cd ../ticketing && mvn exec:java > ../logs/ticketing.log 2>&1 &
TICKETING_PID=$!
echo "Ticketing Service started (PID: $TICKETING_PID)"
sleep 3

cd ../notification && mvn exec:java > ../logs/notification.log 2>&1 &
NOTIFICATION_PID=$!
echo "Notification Service started (PID: $NOTIFICATION_PID)"
sleep 3

cd ../orchestrator && mvn exec:java > ../logs/orchestrator.log 2>&1 &
ORCHESTRATOR_PID=$!
echo "Orchestrator Service started (PID: $ORCHESTRATOR_PID)"

cd ..

echo
echo "=== All services are starting ==="
echo "Service logs are being written to the logs/ directory"
echo "Wait for all services to start, then run: ./start-client.sh"
echo
echo "To stop all services, run: ./stop-services.sh"
echo "Or kill individual services using their PIDs shown above"
echo

# Save PIDs to file for cleanup script
echo "$FLIGHT_SEARCH_PID" > .service-pids
echo "$SEAT_AVAIL_PID" >> .service-pids  
echo "$AUTH_PID" >> .service-pids
echo "$PAYMENT_PID" >> .service-pids
echo "$SEAT_ALLOC_PID" >> .service-pids
echo "$TICKETING_PID" >> .service-pids
echo "$NOTIFICATION_PID" >> .service-pids
echo "$ORCHESTRATOR_PID" >> .service-pids