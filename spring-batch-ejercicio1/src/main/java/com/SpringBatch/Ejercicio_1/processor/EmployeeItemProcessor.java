package com.SpringBatch.Ejercicio_1.processor;


import com.SpringBatch.Ejercicio_1.model.Employee;
import org.springframework.batch.item.ItemProcessor;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class EmployeeItemProcessor implements ItemProcessor<Employee, Employee> {

    @Override
    public Employee process(Employee employee) throws Exception {
        System.out.println("Validating: " + employee.getUserId());

        int id;
        try {
            id = Integer.parseInt(employee.getUserId());
        } catch (NumberFormatException e) {
            throw new Exception("Employee id must be a number!");
        }

        if (id <= 0) {
            throw new Exception("Employee id cannot be negative!");
        }

        // ✅ Si todo está bien, modificamos la fecha
        Date date = employee.getTransactionDate();
        if (date != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            employee.setTransactionDate(calendar.getTime());
        }

        return employee;
    }
}