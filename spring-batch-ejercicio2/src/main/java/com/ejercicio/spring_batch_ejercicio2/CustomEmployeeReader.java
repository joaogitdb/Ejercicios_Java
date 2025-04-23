package com.ejercicio.spring_batch_ejercicio2;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

public class CustomEmployeeReader implements ItemReader<Employee> {

    private DataSource dataSource;
    private boolean initialized = false;
    private List<Employee> employees = new ArrayList<>();
    private int currentIndex = 0;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Employee read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        if (!initialized) {
            try (Connection conn = dataSource.getConnection();
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT user_name, user_id, transaction_date, transaction_amount FROM employee")) {

                while (rs.next()) {
                    Employee emp = new Employee();
                    emp.setUserName(rs.getString("user_name"));
                    emp.setUserId(rs.getString("user_id"));
                    emp.setTransactionDate(rs.getDate("transaction_date"));
                    emp.setTransactionAmount(rs.getInt("transaction_amount"));
                    employees.add(emp);
                }
            }
            initialized = true;
        }
        if (currentIndex < employees.size()) {
            return employees.get(currentIndex++);
        } else {
            return null;
        }
    }
}
