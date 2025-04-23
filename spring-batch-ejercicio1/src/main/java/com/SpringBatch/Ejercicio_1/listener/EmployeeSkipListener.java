package com.SpringBatch.Ejercicio_1.listener;

import org.springframework.batch.core.SkipListener;
import com.SpringBatch.Ejercicio_1.model.Employee;

public class EmployeeSkipListener implements SkipListener<Employee, Employee> {

    private int skipCount = 0;

    @Override
    public void onSkipInRead(Throwable t) {
        System.out.println("❌ Error al leer: " + t.getMessage());
        skipCount++;
    }

    @Override
    public void onSkipInWrite(Employee item, Throwable t) {
        System.out.println("❌ Error al escribir: " + item + " -> " + t.getMessage());
        skipCount++;
    }

    @Override
    public void onSkipInProcess(Employee item, Throwable t) {
        System.out.println("❌ Error al procesar: " + item + " -> " + t.getMessage());
        skipCount++;
    }

    public int getSkipCount() {
        return skipCount;
    }
}