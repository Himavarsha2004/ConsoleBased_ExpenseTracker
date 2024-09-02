package com.expensetracker.service;

import com.expensetracker.model.Admin;
import com.expensetracker.model.User;

public class UserService {

    // Register a user if not already registered
    public void registerUser(User user) {
        if (isUserExists(user.getName())) {
            System.out.println("User already registered.");
            return;
        }
        user.createUser();
        System.out.println("User registration successful.");
    }

    // Authenticate a user
    public User authenticateUser(String name, String password) {
        return User.getUser(name, password);
    }

    // Register an admin (if needed, but you're removing admin functionality)
    public void registerAdmin(Admin admin) {
        // Admin registration logic (if you choose to re-include it)
        admin.createAdmin();
        System.out.println("Admin registration successful.");
    }

    // Authenticate an admin (if needed, but you're removing admin functionality)
    public Admin authenticateAdmin(String name, String password) {
        return Admin.getAdmin(name, password);
    }

    // Check if user already exists in the database
    private boolean isUserExists(String name) {
        return User.getUserByName(name) != null;
    }
}
