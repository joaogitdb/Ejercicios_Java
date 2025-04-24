package com.ejercicio.ejercicio3;

import java.util.List;

import org.springframework.batch.item.ItemWriter;

import com.ejercicio.ejercicio3.dao.EmployeeDao;

public class CustomEmployeeWriter implements ItemWriter<Employee> {
	  
	private EmployeeDao employeeDao;
	    
	    public void setEmployeeDao(EmployeeDao employeeDao) {
	        this.employeeDao = employeeDao;
	    }
	    
	    @Override
	    public void write(List<? extends Employee> items) throws Exception {
	        for (Employee emp : items) {
	            System.out.println("Elimino " + emp.getUserId());
	            employeeDao.delete(emp);
	        }
	    }

}
