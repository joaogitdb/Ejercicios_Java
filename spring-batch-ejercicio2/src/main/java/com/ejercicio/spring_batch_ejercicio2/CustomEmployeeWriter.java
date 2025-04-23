package com.ejercicio.spring_batch_ejercicio2;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.batch.item.ItemWriter;

public class CustomEmployeeWriter implements ItemWriter<Employee> {

    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void write(List<? extends Employee> items) throws Exception {
        try (Connection conn = dataSource.getConnection()) {
            for (Employee emp : items) {
                System.out.println("Elimino " + emp.getUserId());
                // Borrado por ID
                try (PreparedStatement ps = conn.prepareStatement("DELETE FROM employee WHERE user_id = ?")) {
                    ps.setString(1, emp.getUserId());
                    ps.executeUpdate();
                }
            }
        }
    }
}
