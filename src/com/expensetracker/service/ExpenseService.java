package com.expensetracker.service;

import com.expensetracker.model.Expense;

import java.util.List;

public class ExpenseService {
    public void addExpense(Expense expense) {
        expense.addExpense();
    }

    public List<Expense> viewExpenses(int userId) {
        List<Expense> expenses = Expense.getAllExpenses(userId);
        if (expenses.isEmpty()) {
            System.out.println("No recorded expenses in the provided data");
        }
        return expenses;
    }

    public void deleteExpense(int expenseId) {
        Expense expense = new Expense(expenseId, 0, null, null, 0);
        expense.deleteExpense();
    }
}
