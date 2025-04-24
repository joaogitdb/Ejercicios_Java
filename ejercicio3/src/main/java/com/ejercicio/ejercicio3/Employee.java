package com.ejercicio.ejercicio3;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "employee")
public class Employee {

	// Asumimos que userId es la clave primaria
    @Id
    @Column(name = "user_id")
    private String userId;
    @Column(name="user_name")
    private String userName;
    @Column(name="transaction_date")
    private Date transactionDate;
    @Column(name="transaction_amount")
    private int transactionAmount;
    
    public Employee() {
    	
    }
    
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public Date getTransactionDate() {
        return transactionDate;
    }
    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }
    public int getTransactionAmount() {
        return transactionAmount;
    }
    public void setTransactionAmount(int transactionAmount) {
        this.transactionAmount = transactionAmount;
    }
}
