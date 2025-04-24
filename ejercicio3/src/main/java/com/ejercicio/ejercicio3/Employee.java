package com.ejercicio.ejercicio3;         

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;

import java.time.LocalDate;

import javax.persistence.Column;

@Entity                               
@Table(name = "employee")
public class Employee {
    @Id
    @Column(name = "user_id")
    private String userId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "transaction_date")
    private LocalDate transactionDate;

    @Column(name = "transaction_amount")
    private Integer transactionAmount;

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

	public LocalDate getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(LocalDate transactionDate) {
		this.transactionDate = transactionDate;
	}

	public Integer getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(Integer transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	@Override
	public String toString() {
		return "Employee [userId=" + userId + ", userName=" + userName + ", transactionDate=" + transactionDate
				+ ", transactionAmount=" + transactionAmount + "]";
	}

    
}
