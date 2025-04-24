package com.ejercicio.spring_batch_ejercicio2;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.ejercicio.spring_batch_ejercicio2.config.BatchConfig;

public class SpringBatchEjercicio2Application {
    public static void main(String[] args) {
        // Cargar ambos archivos de configuración
        try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(BatchConfig.class)) {
        
        	// Obtener beans
            JobLauncher jobLauncher = ctx.getBean(JobLauncher.class);
            Job job = ctx.getBean("employeeJob", Job.class);

            // Construir los parámetros del job (si necesitas)
            JobExecution exec = jobLauncher.run(
                    job, 
                    new JobParametersBuilder().toJobParameters()
            );

            System.out.println("Exit Status : " + exec.getStatus());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}