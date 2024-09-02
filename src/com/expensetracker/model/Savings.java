package com.expensetracker.model;

import com.expensetracker.util.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Savings {

    /**
     * Calculates the savings for a given user for the current month.
     *
     * @param userId The ID of the user whose savings are to be calculated.
     * @return The calculated savings for the current month.
     */
    public static double calculateMonthlySavings(int userId) {
        double savings = 0;
        // SQL query to calculate savings: salary - total expenses for the current month
        String sql = "SELECT salary - COALESCE(SUM(amount), 0) AS savings " +
                     "FROM users LEFT JOIN expenses ON users.user_id = expenses.user_id " +
                     "WHERE users.user_id = ? " +
                     "AND MONTH(expenses.date) = MONTH(CURDATE()) " +
                     "AND YEAR(expenses.date) = YEAR(CURDATE())";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                savings = rs.getDouble("savings");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return savings;
    }

    /**
     * Calculates the savings for a given user for the current year.
     *
     * @param userId The ID of the user whose savings are to be calculated.
     * @return The calculated savings for the current year.
     */
    public static double calculateYearlySavings(int userId) {
        double savings = 0;
        // SQL query to calculate savings: salary - total expenses for the current year
        String sql = "SELECT salary - COALESCE(SUM(amount), 0) AS savings " +
                     "FROM users LEFT JOIN expenses ON users.user_id = expenses.user_id " +
                     "WHERE users.user_id = ? " +
                     "AND YEAR(expenses.date) = YEAR(CURDATE())";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                savings = rs.getDouble("savings");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return savings;
    }
}
