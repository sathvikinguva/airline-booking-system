#!/bin/bash

echo "=== Starting Airline Booking Client ==="
echo

# Set Maven options for Java 11+ compatibility
export MAVEN_OPTS="--add-opens java.base/java.lang=ALL-UNNAMED --add-opens java.base/java.lang.reflect=ALL-UNNAMED --add-opens java.base/java.io=ALL-UNNAMED --add-opens java.base/java.security=ALL-UNNAMED --add-opens java.base/java.util=ALL-UNNAMED --add-opens java.base/java.lang.invoke=ALL-UNNAMED --add-opens java.base/sun.misc=ALL-UNNAMED"

echo "Demo Users Available:"
echo "- Username: john_doe, Password: password123"
echo "- Username: jane_smith, Password: password456"
echo "- Username: bob_wilson, Password: password789"
echo

read -p "Make sure all services are running! Press Enter to continue..."

cd client
mvn exec:java