package com.SpringBatch.Ejercicio_1.validator;


import com.SpringBatch.Ejercicio_1.model.Employee;
import org.springframework.batch.item.ItemProcessor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EmployeeValidatorProcessor implements ItemProcessor<Employee, Employee> {

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    public Employee process(Employee item) throws Exception {
        System.out.println("Validating: " + item.getUserId());

        // Validar que userId sea numérico
        int id;
        try {
            id = Integer.parseInt(item.getUserId());
        } catch (NumberFormatException e) {
            System.out.println("✖ Error: ID no numérico → " + item.getUserId());
            throw new Exception("Employee id must be a number!");
        }

        // Validar que userId sea positivo
        if (id <= 0) {
            System.out.println("✖ Error: ID negativo → " + item.getUserId());
            throw new Exception("Employee id cannot be negative!");
        }

        // Convertir fecha de String a java.util.Date y sumarle 1 día
        try {
            Date date = dateFormat.parse(item.getTransactionDate());
            long newTime = date.getTime() + (1000 * 60 * 60 * 24); // +1 día
            item.setTransactionDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date(newTime)));
        } catch (ParseException e) {
            throw new Exception("Invalid date format for: " + item.getTransactionDate());
        }

        return item;
    }
}
