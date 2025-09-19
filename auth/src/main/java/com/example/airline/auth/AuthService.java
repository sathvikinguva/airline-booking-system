package com.example.airline.auth;

import com.example.airline.models.User;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@WebService(serviceName = "AuthService")
public class AuthService {
    
    private static final Map<String, User> USER_DATABASE = new ConcurrentHashMap<>();
    private static final Map<String, String> SESSION_TOKENS = new ConcurrentHashMap<>();
    
    static {
        initializeUsers();
    }
    
    private static void initializeUsers() {
        // Initialize with some demo users
        User user1 = new User("user001", "john_doe", "password123", "John", "Doe", "john@example.com", "9876543210");
        user1.setMembershipLevel("GOLD");
        
        User user2 = new User("user002", "jane_smith", "password456", "Jane", "Smith", "jane@example.com", "9876543211");
        user2.setMembershipLevel("SILVER");
        
        User user3 = new User("user003", "bob_wilson", "password789", "Bob", "Wilson", "bob@example.com", "9876543212");
        user3.setMembershipLevel("BRONZE");
        
        USER_DATABASE.put("john_doe", user1);
        USER_DATABASE.put("jane_smith", user2);
        USER_DATABASE.put("bob_wilson", user3);
    }

    @WebMethod
    public String login(@WebParam(name = "username") String username,
                       @WebParam(name = "password") String password) {
        System.out.println("Login attempt for username: " + username);
        
        User user = USER_DATABASE.get(username);
        if (user != null && user.getPassword().equals(password) && user.isActive()) {
            String token = generateSessionToken(user.getUserId());
            SESSION_TOKENS.put(token, user.getUserId());
            System.out.println("Login successful for user: " + username);
            return token;
        }
        
        System.out.println("Login failed for username: " + username);
        return null;
    }

    @WebMethod
    public boolean validateToken(@WebParam(name = "token") String token) {
        boolean isValid = SESSION_TOKENS.containsKey(token);
        System.out.println("Token validation for " + token + ": " + isValid);
        return isValid;
    }

    @WebMethod
    public User getUserByToken(@WebParam(name = "token") String token) {
        String userId = SESSION_TOKENS.get(token);
        if (userId != null) {
            return USER_DATABASE.values().stream()
                    .filter(user -> user.getUserId().equals(userId))
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }

    @WebMethod
    public boolean logout(@WebParam(name = "token") String token) {
        System.out.println("Logout request for token: " + token);
        return SESSION_TOKENS.remove(token) != null;
    }

    @WebMethod
    public User registerUser(@WebParam(name = "username") String username,
                            @WebParam(name = "password") String password,
                            @WebParam(name = "firstName") String firstName,
                            @WebParam(name = "lastName") String lastName,
                            @WebParam(name = "email") String email,
                            @WebParam(name = "phone") String phone) {
        
        System.out.println("Registration attempt for username: " + username);
        
        if (USER_DATABASE.containsKey(username)) {
            System.out.println("Username already exists: " + username);
            return null;
        }
        
        String userId = "user" + String.format("%03d", USER_DATABASE.size() + 1);
        User newUser = new User(userId, username, password, firstName, lastName, email, phone);
        USER_DATABASE.put(username, newUser);
        
        System.out.println("User registered successfully: " + username);
        return newUser;
    }

    @WebMethod
    public boolean changePassword(@WebParam(name = "token") String token,
                                @WebParam(name = "oldPassword") String oldPassword,
                                @WebParam(name = "newPassword") String newPassword) {
        
        String userId = SESSION_TOKENS.get(token);
        if (userId == null) {
            return false;
        }
        
        User user = USER_DATABASE.values().stream()
                .filter(u -> u.getUserId().equals(userId))
                .findFirst()
                .orElse(null);
        
        if (user != null && user.getPassword().equals(oldPassword)) {
            user.setPassword(newPassword);
            System.out.println("Password changed for user: " + user.getUsername());
            return true;
        }
        
        return false;
    }

    @WebMethod
    public boolean updateProfile(@WebParam(name = "token") String token,
                               @WebParam(name = "firstName") String firstName,
                               @WebParam(name = "lastName") String lastName,
                               @WebParam(name = "email") String email,
                               @WebParam(name = "phone") String phone,
                               @WebParam(name = "address") String address) {
        
        String userId = SESSION_TOKENS.get(token);
        if (userId == null) {
            return false;
        }
        
        User user = USER_DATABASE.values().stream()
                .filter(u -> u.getUserId().equals(userId))
                .findFirst()
                .orElse(null);
        
        if (user != null) {
            if (firstName != null && !firstName.trim().isEmpty()) user.setFirstName(firstName);
            if (lastName != null && !lastName.trim().isEmpty()) user.setLastName(lastName);
            if (email != null && !email.trim().isEmpty()) user.setEmail(email);
            if (phone != null && !phone.trim().isEmpty()) user.setPhone(phone);
            if (address != null && !address.trim().isEmpty()) user.setAddress(address);
            
            System.out.println("Profile updated for user: " + user.getUsername());
            return true;
        }
        
        return false;
    }

    @WebMethod
    public boolean deactivateUser(@WebParam(name = "token") String token) {
        String userId = SESSION_TOKENS.get(token);
        if (userId == null) {
            return false;
        }
        
        User user = USER_DATABASE.values().stream()
                .filter(u -> u.getUserId().equals(userId))
                .findFirst()
                .orElse(null);
        
        if (user != null) {
            user.setActive(false);
            // Remove all sessions for this user
            SESSION_TOKENS.entrySet().removeIf(entry -> entry.getValue().equals(userId));
            System.out.println("User deactivated: " + user.getUsername());
            return true;
        }
        
        return false;
    }

    private String generateSessionToken(String userId) {
        return "TOKEN_" + userId + "_" + System.currentTimeMillis() + "_" + 
               new Random().nextInt(10000);
    }

    @WebMethod
    public List<String> getActiveSessions() {
        System.out.println("Getting active sessions count: " + SESSION_TOKENS.size());
        return new ArrayList<>(SESSION_TOKENS.keySet());
    }
}