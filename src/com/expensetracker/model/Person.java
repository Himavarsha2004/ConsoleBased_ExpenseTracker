package com.expensetracker.model;

public abstract class Person {
    private int userId;
    private String name;
    private String password;

    // Constructor
    public Person(int userId, String name, String password) {
        this.userId = userId;
        this.name = name;
        this.password = password;
    }

    // Getters and Setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Abstract method to be overridden
    public abstract void displayInfo();
}
