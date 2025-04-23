package com.ejercicio.spring_batch_ejercicio2;





import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringBatchEjercicio2Application {
    public static void main(String[] args) {
        // Cargar ambos archivos de configuración
        try (ClassPathXmlApplicationContext context = 
                new ClassPathXmlApplicationContext("context.xml", "job.xml")) {

            // Obtener beans
            JobLauncher jobLauncher = context.getBean("jobLauncher", JobLauncher.class);
            Job job = context.getBean("employeeJob", Job.class);

            // Construir los parámetros del job (si necesitas)
            JobExecution execution = jobLauncher.run(
                    job, 
                    new JobParametersBuilder().toJobParameters()
            );

            System.out.println("Exit Status : " + execution.getStatus());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}