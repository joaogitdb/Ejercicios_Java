package com.SpringBatch.Ejercicio_1.processor;

import com.SpringBatch.Ejercicio_1.model.Employee;
import org.springframework.batch.item.ItemProcessor;

import java.util.Calendar;

public class EmployeeItemProcessor implements ItemProcessor<Employee, Employee> {
    @Override
    public Employee process(Employee emp) throws Exception {
        Calendar c = Calendar.getInstance();
        c.setTime(emp.getTransactionDate());
        c.add(Calendar.DATE, 1);
        emp.setTransactionDate(c.getTime());
        System.out.println("Processing: " + emp.getUserId());
        return emp;
    }
}
