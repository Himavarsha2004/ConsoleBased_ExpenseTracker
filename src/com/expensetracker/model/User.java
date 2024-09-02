package com.expensetracker.model;

import com.expensetracker.util.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User extends Person {
    private int age;
    private double salary;

    // Constructor
    public User(int userId, String name, String password, int age, double salary) {
        super(userId, name, password); // Calling the constructor of the parent class (Person)
        this.age = age;
        this.salary = salary;
    }

    // Getters and Setters
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    // Overriding the abstract method from Person class
    @Override
    public void displayInfo() {
        System.out.println("User ID: " + getUserId());
        System.out.println("Name: " + getName());
        System.out.println("Age: " + age);
        System.out.println("Salary: " + salary);
    }

    // Method to create a new user in the database
    public void createUser() {
        // Check if user already exists
        if (getUserByName(getName()) != null) {
            System.out.println("User already registered.");
            return;
        }

        String sql = "INSERT INTO users (name, password, age, salary) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, getName());
            pstmt.setString(2, getPassword());
            pstmt.setInt(3, age);
            pstmt.setDouble(4, salary);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Static method to retrieve a user from the database by name and password
    public static User getUser(String name, String password) {
        String sql = "SELECT * FROM users WHERE name = ? AND password = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getInt("user_id"),
                        rs.getString("name"),
                        rs.getString("password"),
                        rs.getInt("age"),
                        rs.getDouble("salary")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Static method to check if a user exists by name
    public static User getUserByName(String name) {
        String sql = "SELECT * FROM users WHERE name = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getInt("user_id"),
                        rs.getString("name"),
                        rs.getString("password"),
                        rs.getInt("age"),
                        rs.getDouble("salary")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
