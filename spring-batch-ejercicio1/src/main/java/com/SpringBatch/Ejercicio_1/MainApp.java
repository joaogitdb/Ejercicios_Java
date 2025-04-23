package com.SpringBatch.Ejercicio_1;


import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.SpringBatch.Ejercicio_1.config.BatchConfiguration;

public class MainApp {
	 public static void main(String[] args) throws Exception {
	        // Carga la configuración basada en clases Java (no XML)
	        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(BatchConfiguration.class);

	        JobLauncher jobLauncher = context.getBean(JobLauncher.class);
	        Job job = context.getBean("importEmployeeJob", Job.class);

	        // Ejecuta el job sin parámetros
	        JobExecution execution = jobLauncher.run(job, new JobParameters());

	        System.out.println("Exit Status: " + execution.getStatus());

	        context.close();
	    }
}