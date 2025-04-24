package com.ejercicio.ejercicio3;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.batch.BatchAutoConfiguration;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@SpringBootApplication(exclude = { BatchAutoConfiguration.class })
public class Ejercicio3Application {

	public static void main(String[] args) {
		
		try (ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("context.xml", "job.xml")) {
            
            JobLauncher jobLauncher = context.getBean("jobLauncher", JobLauncher.class);
            Job job = context.getBean("employeeJob", Job.class);
            
            JobExecution execution = jobLauncher.run(
                    job, new JobParametersBuilder().toJobParameters());
            System.out.println("Exit Status : " + execution.getStatus());
            
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

}
