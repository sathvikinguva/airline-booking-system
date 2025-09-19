#!/bin/bash

echo "=== Stopping All Airline Booking Services ==="
echo

# Read PIDs from PID files if they exist
PID_FILES=(
    "orchestrator/.pid"
    "flight-search/.pid"
    "seat-availability/.pid"
    "auth/.pid"
    "payment/.pid"
    "seat-allocation/.pid"
    "ticketing/.pid"
    "notification/.pid"
)

KILLED_COUNT=0

for pidfile in "${PID_FILES[@]}"; do
    if [ -f "$pidfile" ]; then
        PID=$(cat "$pidfile")
        if kill -0 "$PID" 2>/dev/null; then
            echo "Stopping process $PID from $pidfile"
            kill "$PID"
            rm "$pidfile"
            KILLED_COUNT=$((KILLED_COUNT + 1))
        else
            echo "Process $PID from $pidfile is not running"
            rm "$pidfile"
        fi
    fi
done

# Also kill any remaining Maven exec processes for this project
MAVEN_PIDS=$(ps aux | grep "maven" | grep -E "(orchestrator|flight-search|seat-availability|auth|payment|seat-allocation|ticketing|notification)" | grep -v grep | awk '{print $2}')

if [ -n "$MAVEN_PIDS" ]; then
    echo "Stopping remaining Maven processes..."
    echo "$MAVEN_PIDS" | xargs kill 2>/dev/null
    KILLED_COUNT=$((KILLED_COUNT + $(echo "$MAVEN_PIDS" | wc -l)))
fi

if [ $KILLED_COUNT -gt 0 ]; then
    echo "Stopped $KILLED_COUNT service(s)"
else
    echo "No services were running"
fi

echo "All services stopped!"