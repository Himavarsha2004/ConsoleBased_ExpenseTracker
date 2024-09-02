package com.expensetracker.main;

import com.expensetracker.model.User;
import com.expensetracker.model.Expense;
import com.expensetracker.model.Savings;
import com.expensetracker.service.ExpenseService;
import com.expensetracker.service.UserService;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static User currentUser;
    private static UserService userService = new UserService();
    private static ExpenseService expenseService = new ExpenseService();

    public static void main(String[] args) {
        while (true) {
            System.out.println("1. Register as User");
            System.out.println("2. Login as User");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int option = getValidIntegerInput();

            switch (option) {
                case 1:
                    registerUser();
                    break;
                case 2:
                    loginUser();
                    break;
                case 3:
                    System.out.println("Exiting the application. Goodbye!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void registerUser() {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        System.out.print("Enter age: ");
        int age = getValidIntegerInput();
        System.out.print("Enter salary: ");
        double salary = getValidDoubleInput();

        User user = new User(0, name, password, age, salary);
        userService.registerUser(user);
        System.out.println("User registration successful.");
    }

    private static void loginUser() {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        currentUser = userService.authenticateUser(name, password);
        if (currentUser != null) {
            System.out.println("User login successful.");
            userMenu();
        } else {
            System.out.println("Invalid credentials. Please try again.");
        }
    }

    private static void userMenu() {
        while (true) {
            System.out.println("1. Add Expense");
            System.out.println("2. View Expenses");
            System.out.println("3. View Savings");
            System.out.println("4. Delete Expense");
            System.out.println("5. Logout");
            System.out.print("Choose an option: ");
            int option = getValidIntegerInput();

            switch (option) {
                case 1:
                    addExpense();
                    break;
                case 2:
                    viewExpenses();
                    break;
                case 3:
                    viewSavings();
                    break;
                case 4:
                    deleteExpense();
                    break;
                case 5:
                    System.out.println("Logging out...");
                    currentUser = null;
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void addExpense() {
        System.out.print("Enter date (YYYY-MM-DD): ");
        String date = scanner.nextLine();
        System.out.print("Enter description: ");
        String description = scanner.nextLine();
        System.out.print("Enter amount: ");
        double amount = getValidDoubleInput();

        Expense expense = new Expense(0, currentUser.getUserId(), date, description, amount);
        expenseService.addExpense(expense);
        System.out.println("Expense added successfully.");
    }

    private static void viewExpenses() {
        List<Expense> expenses = expenseService.viewExpenses(currentUser.getUserId());
        if (expenses.isEmpty()) {
            System.out.println("No recorded expenses.");
            return;
        }
        for (Expense expense : expenses) {
            System.out.println("ID: " + expense.getExpenseId() + ", Date: " + expense.getDate() + ", Description: " + expense.getDescription() + ", Amount: " + expense.getAmount());
        }
    }

    private static void viewSavings() {
        double monthlySavings = Savings.calculateMonthlySavings(currentUser.getUserId());
        double yearlySavings = Savings.calculateYearlySavings(currentUser.getUserId());
        System.out.println("Monthly savings: " + monthlySavings);
        System.out.println("Yearly savings: " + yearlySavings);
    }

    private static void deleteExpense() {
        System.out.print("Enter expense ID to delete: ");
        int expenseId = getValidIntegerInput();

        List<Expense> expenses = expenseService.viewExpenses(currentUser.getUserId());
        boolean found = false;
        for (Expense expense : expenses) {
            if (expense.getExpenseId() == expenseId) {
                expenseService.deleteExpense(expenseId);
                System.out.println("Expense deleted successfully.");
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Expense ID not found.");
        }
    }

    private static int getValidIntegerInput() {
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a valid integer.");
            scanner.next(); // Clear invalid input
        }
        return scanner.nextInt();
    }

    private static double getValidDoubleInput() {
        while (!scanner.hasNextDouble()) {
            System.out.println("Invalid input. Please enter a valid number.");
            scanner.next(); // Clear invalid input
        }
        return scanner.nextDouble();
    }
}
