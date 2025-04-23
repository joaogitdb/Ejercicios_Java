package com.SpringBatch.Ejercicio_1;


import com.SpringBatch.Ejercicio_1.config.BatchConfiguration;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainApp {
    public static void main(String[] args) throws Exception {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(BatchConfiguration.class);

        JobLauncher jobLauncher = context.getBean(JobLauncher.class);
        Job job = context.getBean("importEmployeeJob", Job.class);

        JobExecution execution = jobLauncher.run(job, new JobParameters());
        System.out.println("Exit Status: " + execution.getStatus());

        context.close();
    }
}