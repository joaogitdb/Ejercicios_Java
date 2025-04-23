package com.SpringBatch.Ejercicio_1.validator;

import com.SpringBatch.Ejercicio_1.model.Employee;
import org.springframework.batch.item.ItemProcessor;

public class EmployeeValidatorProcessor implements ItemProcessor<Employee, Employee> {

	@Override
    public Employee process(Employee employee) throws Exception {
        try {
            int id = Integer.parseInt(employee.getUserId());
            if (id < 0) {
                throw new Exception("Employee id cannot be negative!");
            }
        } catch (NumberFormatException e) {
            throw new Exception("Invalid userId: must be numeric", e);
        }

        return employee; // ← Devuelve el objeto si está todo bien
    }
}