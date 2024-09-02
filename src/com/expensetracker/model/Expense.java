package com.expensetracker.model;

import com.expensetracker.util.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Expense {
    private int expenseId;
    private int userId;
    private String date;
    private String description;
    private double amount;

    // Constructor
    public Expense(int expenseId, int userId, String date, String description, double amount) {
        this.expenseId = expenseId;
        this.userId = userId;
        this.date = date;
        this.description = description;
        this.amount = amount;
    }

    // Getters and Setters
    public int getExpenseId() {
        return expenseId;
    }

    public int getUserId() {
        return userId;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }

    // Method to add an expense to the database
    public void addExpense() {
        String sql = "INSERT INTO expenses (user_id, date, description, amount) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setString(2, date);
            pstmt.setString(3, description);
            pstmt.setDouble(4, amount);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Static method to retrieve all expenses for a specific user
    public static List<Expense> getAllExpenses(int userId) {
        List<Expense> expenses = new ArrayList<>();
        String sql = "SELECT * FROM expenses WHERE user_id = ? ORDER BY date";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                expenses.add(new Expense(
                        rs.getInt("expense_id"),
                        rs.getInt("user_id"),
                        rs.getString("date"),
                        rs.getString("description"),
                        rs.getDouble("amount")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return expenses;
    }

    // Method to delete an expense from the database
    public void deleteExpense() {
        String sql = "DELETE FROM expenses WHERE expense_id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, expenseId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
