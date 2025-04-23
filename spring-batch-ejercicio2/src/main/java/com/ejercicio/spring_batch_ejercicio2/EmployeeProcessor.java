package com.ejercicio.spring_batch_ejercicio2;

import org.springframework.batch.item.ItemProcessor;

public class EmployeeProcessor implements ItemProcessor<Employee, Employee> {

    @Override
    public Employee process(Employee item) throws Exception {
        // Logs de validación y procesamiento
        System.out.println("Voy a validar a " + item.getUserId());
        System.out.println("Processing..." + item.getUserId());
        // Aquí podrías meter lógica de validación, por ejemplo omitir algunos Employee
        // si no cumplen alguna condición. Pero en este ejemplo simplemente retornamos el mismo item.
        return item;
    }
}
