package com.ejercicio.ejercicio3.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ejercicio.ejercicio3.CustomEmployeeReader;
import com.ejercicio.ejercicio3.CustomEmployeeWriter;
import com.ejercicio.ejercicio3.Employee;
import com.ejercicio.ejercicio3.EmployeeProcessor;
import com.ejercicio.ejercicio3.dao.EmployeeDao;

@Configuration
@EnableBatchProcessing
public class BatchConfig {
	
	//Inyacta las funciones de Spring Batch y las configura
	private final JobBuilderFactory jobs;
	private final StepBuilderFactory steps;
	private final EmployeeDao employeeDao;
	
	public BatchConfig (JobBuilderFactory jobs, StepBuilderFactory steps, EmployeeDao employeeDao) {
		this.jobs = jobs;
		this.steps = steps;
		this.employeeDao = employeeDao;
		
	}
	
	@Bean
	public CustomEmployeeReader customEmployeeReader() {
		CustomEmployeeReader reader = new CustomEmployeeReader();
		reader.setEmployeeDao(employeeDao);
		return reader;
	}
	
	@Bean
	public EmployeeProcessor employeeProcessor() {
		return new EmployeeProcessor();
	}
	
	@Bean
	public CustomEmployeeWriter customEmployeeWriter() {
		CustomEmployeeWriter writer = new CustomEmployeeWriter();
		writer.setEmployeeDao(employeeDao);
		return writer;
	}
	
	@Bean
	public Step step1() {
		return steps.get("step1")
				.<Employee, Employee>chunk(10)  // Define el tamaño del chunk(intervalo de registros a procesar)
				.reader(customEmployeeReader())
				.processor(employeeProcessor())
				.writer(customEmployeeWriter())
				.build();
	}
	
	@Bean
	public Job employeeJob() {
		return jobs.get("employeeJob")
				.start(step1())
				.build();
	}

}
