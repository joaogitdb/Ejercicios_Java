package com.SpringBatch.Ejercicio_1.validator;
import com.SpringBatch.Ejercicio_1.model.Employee;
import com.SpringBatch.Ejercicio_1.processor.EmployeeItemProcessor;

import org.springframework.batch.item.ItemProcessor;

public class EmployeeValidatorProcessor implements ItemProcessor<Employee, Employee> {

	private final EmployeeItemProcessor delegate = new EmployeeItemProcessor();
	
	@Override
    public Employee process(Employee employee) throws Exception {
        // Validación personalizada
        if (Integer.parseInt(employee.getUserId()) < 0) {
            throw new Exception("Employee id cannot be negative!");
        }

        // Procesamiento con el delegado
        return delegate.process(employee);
    }
}