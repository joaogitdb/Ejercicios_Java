package com.SpringBatch.Ejercicio_1.model;

public class Employee {
    private String userName;
    private String userId;
    private String transactionDate;  // Usamos String para el mapeo desde el CSV
    private int transactionAmount;

    // Getters y Setters
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public int getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(int transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "userName='" + userName + '\'' +
                ", userId='" + userId + '\'' +
                ", transactionDate='" + transactionDate + '\'' +
                ", transactionAmount=" + transactionAmount +
                '}';
    }
}
