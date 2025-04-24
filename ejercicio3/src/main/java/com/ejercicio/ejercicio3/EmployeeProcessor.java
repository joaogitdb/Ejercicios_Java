package com.ejercicio.ejercicio3;

import org.springframework.batch.item.ItemProcessor;

public class EmployeeProcessor implements ItemProcessor<Employee, Employee> {

	 @Override
	    public Employee process(Employee item) throws Exception {
	        System.out.println("Voy a validar a " + item.getUserId());
	        System.out.println("Processing... " + item.getUserId());
	        // Puedes agregar lógica de validación o transformación si lo necesitas.
	        return item;
	    }
}
