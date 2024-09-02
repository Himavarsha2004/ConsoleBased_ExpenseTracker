package com.expensetracker.model;

import com.expensetracker.util.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Admin extends Person {
    private String role; // Specific field for Admin

    // Constructor
    public Admin(int userId, String name, String password, String role) {
        super(userId, name, password); // Calling the constructor of the parent class (Person)
        this.role = role;
    }

    // Getters and Setters
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    // Overriding the abstract method from Person class
    @Override
    public void displayInfo() {
        System.out.println("Admin ID: " + getUserId());
        System.out.println("Name: " + getName());
        System.out.println("Role: " + role);
    }

    // Method to add an admin to the database
    public void createAdmin() {
        String sql = "INSERT INTO admins (name, password, role) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, getName());
            pstmt.setString(2, getPassword());
            pstmt.setString(3, role);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Static method to retrieve an admin from the database
    public static Admin getAdmin(String name, String password) {
        String sql = "SELECT * FROM admins WHERE name = ? AND password = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Admin(
                        rs.getInt("admin_id"),
                        rs.getString("name"),
                        rs.getString("password"),
                        rs.getString("role")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Additional functionality specific to Admin
    public void viewAllUsers() {
        String sql = "SELECT * FROM users";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                System.out.println("User ID: " + rs.getInt("user_id"));
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("Age: " + rs.getInt("age"));
                System.out.println("Salary: " + rs.getDouble("salary"));
                System.out.println("---------------");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
