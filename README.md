# Airline Booking Orchestration System

A comprehensive airline booking system built with **Java 8**, **SOAP Web Services**, and **Maven** in VS Code. This project demonstrates a microservices architecture with service orchestration for airline ticket booking.

## Architecture Overview

The system consists of 9 independent SOAP web services coordinated by an orchestrator:

```
┌─────────────────┐    ┌──────────────────┐    ┌─────────────────┐
│   Client App    │────│   Orchestrator   │────│  All Services   │
└─────────────────┘    └──────────────────┘    └─────────────────┘
                                │
                    ┌───────────┴───────────┐
                    │                       │
            ┌───────▼────────┐     ┌───────▼────────┐
            │ Flight Search  │     │ Seat Available │
            │ Service :8081  │     │ Service :8082  │
            └────────────────┘     └────────────────┘
                    │                       │
            ┌───────▼────────┐     ┌───────▼────────┐
            │ Authentication │     │ Payment Service│
            │ Service :8083  │     │ Service :8084  │
            └────────────────┘     └────────────────┘
                    │                       │
            ┌───────▼────────┐     ┌───────▼────────┐
            │ Seat Allocation│     │ Ticketing Svc  │
            │ Service :8085  │     │ Service :8086  │
            └────────────────┘     └────────────────┘
                    │
            ┌───────▼────────┐
            │ Notification   │
            │ Service :8087  │
            └────────────────┘
```

## Prerequisites

- **Java 8 or higher** (JDK 8+)
- **Apache Maven 3.6+**
- **VS Code** with Java Extension Pack (optional but recommended)

### Verify Installation
```bash
java -version
mvn -version
```

## Quick Start

### 1. Clone and Build the Project
```bash
git clone <repository-url>
cd airline-booking-system
mvn clean install
```

### 2. Start Services (in separate terminals)

**Terminal 1 - Flight Search Service:**
```bash
cd flight-search
mvn exec:java
```

**Terminal 2 - Seat Availability Service:**
```bash
cd seat-availability
mvn exec:java
```

**Terminal 3 - Authentication Service:**
```bash
cd auth
mvn exec:java
```

**Terminal 4 - Payment Service:**
```bash
cd payment
mvn exec:java
```

**Terminal 5 - Seat Allocation Service:**
```bash
cd seat-allocation
mvn exec:java
```

**Terminal 6 - Ticketing Service:**
```bash
cd ticketing
mvn exec:java
```

**Terminal 7 - Notification Service:**
```bash
cd notification
mvn exec:java
```

**Terminal 8 - Orchestrator Service:**
```bash
cd orchestrator
mvn exec:java
```

### 3. Run Client Application
```bash
cd client
mvn exec:java
```

### Alternative: Use Convenience Scripts

**For Windows:**
```bash
# Start all services
.\start-all-services.bat

# Start client (after services are running)
.\start-client.bat
```

**For Unix/Linux/macOS:**
```bash
# Make scripts executable
chmod +x start-all-services.sh start-client.sh

# Start all services
./start-all-services.sh

# Start client (after services are running)
./start-client.sh
```

## Service Endpoints

| Service | URL | WSDL |
|---------|-----|------|
| Flight Search | http://localhost:8081/flightsearch | [WSDL](http://localhost:8081/flightsearch?wsdl) |
| Seat Availability | http://localhost:8082/seatavailability | [WSDL](http://localhost:8082/seatavailability?wsdl) |
| Authentication | http://localhost:8083/auth | [WSDL](http://localhost:8083/auth?wsdl) |
| Payment | http://localhost:8084/payment | [WSDL](http://localhost:8084/payment?wsdl) |
| Seat Allocation | http://localhost:8085/seatallocation | [WSDL](http://localhost:8085/seatallocation?wsdl) |
| Ticketing | http://localhost:8086/ticketing | [WSDL](http://localhost:8086/ticketing?wsdl) |
| Notification | http://localhost:8087/notification | [WSDL](http://localhost:8087/notification?wsdl) |
| **Orchestrator** | http://localhost:8090/orchestrator | [WSDL](http://localhost:8090/orchestrator?wsdl) |

## Demo Users

| Username | Password | Membership |
|----------|----------|------------|
| john_doe | password123 | GOLD |
| jane_smith | password456 | SILVER |
| bob_wilson | password789 | BRONZE |

## Sample Flights

| Flight ID | Number | Airline | Route | Price |
|-----------|--------|---------|-------|-------|
| FL001 | AI101 | Air India | Delhi → Mumbai | $5,500 |
| FL002 | AI102 | Air India | Mumbai → Delhi | $5,500 |
| FL003 | SG201 | SpiceJet | Delhi → Bangalore | $4,200 |
| FL004 | SG202 | SpiceJet | Bangalore → Delhi | $4,200 |
| FL005 | IG301 | IndiGo | Delhi → Chennai | $4,800 |

## Booking Workflow

1. **User Authentication** - Login with username/password
2. **Flight Search** - Search available flights by route/date
3. **Seat Availability** - Check available seats on selected flight
4. **Seat Selection** - Choose seat preferences (WINDOW/AISLE/MIDDLE)
5. **Payment Processing** - Process payment with card details
6. **Seat Allocation** - Allocate specific seats to passenger
7. **Ticket Generation** - Generate ticket with confirmation code
8. **Notification** - Send booking confirmation via email/SMS

## Project Structure

```
airline-booking-system/
├── models/                 # Shared data models
├── flight-search/          # Flight search service
├── seat-availability/      # Seat availability service  
├── auth/                   # Authentication service
├── payment/                # Payment processing service
├── seat-allocation/        # Seat allocation service
├── ticketing/              # Ticket generation service
├── notification/           # Notification service
├── orchestrator/           # Main orchestration service
├── client/                 # Client application
└── pom.xml                 # Root Maven configuration
```

## Testing the System

### Manual Testing with Client
1. Start all services
2. Run the client application
3. Follow the interactive prompts

### WSDL Testing
Visit any service WSDL URL (e.g., http://localhost:8081/flightsearch?wsdl) to see the service contract.

### SOAP UI Testing
Import WSDL URLs into SOAP UI for advanced testing.

## Troubleshooting

**Port Already in Use:**
```bash
# Linux/macOS
lsof -i :8081
kill -9 <PID>

# Windows
netstat -ano | findstr :8081
taskkill /PID <PID> /F
```

**Service Not Starting:**
- Ensure Java 8+ is installed and JAVA_HOME is set
- Check if Maven dependencies are downloaded
- Verify no firewall blocking the ports
- For Java 11+, use the provided PowerShell scripts with JVM module arguments

**Build Errors:**
```bash
mvn clean install -U
```

**Java 11+ Compatibility Issues:**
If you encounter module system errors with Java 11+, use the PowerShell scripts which include the necessary JVM arguments:
```powershell
# Windows - Use these scripts for Java 11+ compatibility
.\start-all-services.ps1
.\start-client.ps1
```

## Development in VS Code

### Required Extensions
- Extension Pack for Java
- Maven for Java  
- Debugger for Java

### Running Individual Services
1. Open the project root folder in VS Code
2. Navigate to any service subfolder 
3. Press F5 or use "Run Java" button in the service's main class
4. Services will start with automatic endpoint publishing

### Debugging
- Set breakpoints in service methods
- Use VS Code debugger to step through orchestration flow
- Monitor service logs in integrated terminal
- Each service can be debugged independently

## Features Implemented

**SOAP Web Services** with JAX-WS  
**Service Orchestration** with centralized coordinator  
**Authentication & Authorization** with session tokens  
**Payment Processing** with mock payment gateway  
**Seat Management** with allocation and blocking  
**Ticket Generation** with unique confirmation codes  
**Notification System** with email/SMS simulation  
**Error Handling** with rollback mechanisms  
**Multi-module Maven** project structure  
**Interactive Client** application  

## Future Enhancements

- Database integration (MySQL/PostgreSQL)
- Real payment gateway integration (Stripe/PayPal)
- Email service integration (SendGrid/AWS SES)
- Docker containerization
- RESTful API alongside SOAP
- Web UI with Spring Boot
- Redis caching for seat availability
- Message queues with RabbitMQ/ActiveMQ

## License

This project is for educational and demonstration purposes. Feel free to use, modify, and distribute as needed.

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## Support

If you encounter any issues or have questions, please:
1. Check the troubleshooting section above
2. Review the service logs for error messages
3. Open an issue in the GitHub repository

---

**Project:** Airline Booking Orchestration System  
**Technology Stack:** Java, SOAP Web Services, Maven, JAX-WS