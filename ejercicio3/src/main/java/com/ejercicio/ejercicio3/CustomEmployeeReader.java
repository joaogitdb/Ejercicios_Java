package com.ejercicio.ejercicio3;

import java.util.List;
import org.springframework.batch.item.ItemReader;
import com.ejercicio.ejercicio3.dao.EmployeeDao;

public class CustomEmployeeReader implements ItemReader<Employee> {


    private EmployeeDao employeeDao;
    private List<Employee> employeeList;
    private int currentIndex = 0;
    
    public void setEmployeeDao(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }
    
    @Override
    public Employee read() throws Exception {
        if (employeeList == null) {
            employeeList = employeeDao.findMinAmount(2000);
            System.out.println("Inicializamos");
        }
        if (currentIndex < employeeList.size()) {
            System.out.println("Leemos");
            return employeeList.get(currentIndex++);
        } else {
            return null;
        }
    }
}
