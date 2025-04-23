package com.SpringBatch.Ejercicio_1.writer;

import com.SpringBatch.Ejercicio_1.model.Employee;
import org.springframework.batch.item.database.ItemPreparedStatementSetter;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EmployeePreparedStatementSetter implements ItemPreparedStatementSetter<Employee> {
    @Override
    public void setValues(Employee emp, PreparedStatement ps) throws SQLException {
        ps.setString(1, emp.getUserName());
        ps.setString(2, emp.getUserId());
        ps.setDate(3, new java.sql.Date(emp.getTransactionDate().getTime()));
        ps.setInt(4, emp.getTransactionAmount());
    }
}
