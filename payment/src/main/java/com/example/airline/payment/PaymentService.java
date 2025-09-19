package com.example.airline.payment;

import com.example.airline.models.PaymentDetails;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.Random;

@WebService(serviceName = "PaymentService")
public class PaymentService {
    
    private Random random = new Random();

    @WebMethod
    public String processPayment(@WebParam(name = "paymentDetails") PaymentDetails paymentDetails,
                               @WebParam(name = "amount") double amount,
                               @WebParam(name = "bookingId") String bookingId) {
        
        System.out.println("Processing payment for booking: " + bookingId + ", Amount: $" + amount);
        
        // Mock payment validation
        if (paymentDetails == null) {
            return "FAILED:Invalid payment details";
        }
        
        if (amount <= 0) {
            return "FAILED:Invalid amount";
        }
        
        // Simulate payment processing delay
        try {
            Thread.sleep(1000 + random.nextInt(2000)); // 1-3 seconds
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Mock success rate of 90%
        if (random.nextDouble() > 0.1) {
            String transactionId = "TXN" + System.currentTimeMillis() + random.nextInt(1000);
            System.out.println("Payment successful. Transaction ID: " + transactionId);
            return "SUCCESS:" + transactionId;
        } else {
            System.out.println("Payment failed - insufficient funds or card declined");
            return "FAILED:Payment declined";
        }
    }

    @WebMethod
    public boolean refundPayment(@WebParam(name = "transactionId") String transactionId,
                               @WebParam(name = "amount") double amount) {
        System.out.println("Processing refund for transaction: " + transactionId + ", Amount: $" + amount);
        
        // Mock refund processing (always succeeds for demo)
        try {
            Thread.sleep(500 + random.nextInt(1000));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        System.out.println("Refund processed successfully");
        return true;
    }
}