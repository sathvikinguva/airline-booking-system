#!/bin/bash

echo "=== Checking Status of All Airline Booking Services ==="
echo

SERVICES=(
    "flight-search:8081"
    "seat-availability:8082"
    "auth:8083"
    "payment:8084"
    "seat-allocation:8085"
    "ticketing:8086"
    "notification:8087"
    "orchestrator:8090"
)

RUNNING_COUNT=0
TOTAL_COUNT=${#SERVICES[@]}

for service in "${SERVICES[@]}"; do
    SERVICE_NAME=$(echo "$service" | cut -d: -f1)
    SERVICE_PORT=$(echo "$service" | cut -d: -f2)
    
    if nc -z localhost "$SERVICE_PORT" 2>/dev/null; then
        echo "✅ $SERVICE_NAME (port $SERVICE_PORT) - RUNNING"
        RUNNING_COUNT=$((RUNNING_COUNT + 1))
    else
        echo "❌ $SERVICE_NAME (port $SERVICE_PORT) - NOT RUNNING"
    fi
done

echo
echo "Status Summary: $RUNNING_COUNT/$TOTAL_COUNT services running"

if [ $RUNNING_COUNT -eq $TOTAL_COUNT ]; then
    echo "🎉 All services are running! You can start the client."
elif [ $RUNNING_COUNT -eq 0 ]; then
    echo "⚠️  No services are running. Run ./start-all-services.sh first."
else
    echo "⚠️  Some services are not running. You may need to restart them."
fi

# Check for PID files
echo
echo "PID Files Status:"
PID_FILES=("orchestrator/.pid" "flight-search/.pid" "seat-availability/.pid" "auth/.pid" "payment/.pid" "seat-allocation/.pid" "ticketing/.pid" "notification/.pid")

for pidfile in "${PID_FILES[@]}"; do
    if [ -f "$pidfile" ]; then
        PID=$(cat "$pidfile")
        if kill -0 "$PID" 2>/dev/null; then
            echo "  📝 $pidfile: PID $PID (running)"
        else
            echo "  💀 $pidfile: PID $PID (dead)"
        fi
    else
        echo "  ❓ $pidfile: not found"
    fi
done