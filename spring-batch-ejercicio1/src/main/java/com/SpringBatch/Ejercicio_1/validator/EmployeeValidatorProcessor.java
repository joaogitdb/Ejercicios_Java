package com.SpringBatch.Ejercicio_1.validator;

import com.SpringBatch.Ejercicio_1.model.Employee;
import org.springframework.batch.item.ItemProcessor;

public class EmployeeValidatorProcessor implements ItemProcessor<Employee, Employee> {
    @Override
    public Employee process(Employee emp) throws Exception {
        System.out.println("Validating: " + emp.getUserId());
        try {
            int id = Integer.parseInt(emp.getUserId());
            if (id <= 0) throw new Exception("Employee id cannot be negative!");
        } catch (NumberFormatException e) {
            throw new Exception("Employee id must be a number!");
        }
        return emp;
    }
}
