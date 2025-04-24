package com.ejercicio.ejercicio3.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ComponentScan;

import com.ejercicio.ejercicio3.CustomEmployeeReader;
import com.ejercicio.ejercicio3.CustomEmployeeWriter;
import com.ejercicio.ejercicio3.dao.EmployeeDao;
import com.ejercicio.ejercicio3.Employee;

@Configuration
@EnableBatchProcessing
@ComponentScan("com.ejercicio.ejercicio3.dao")  // para que detecte tu EmployeeDao
public class BatchConfig {

    // 1) Define el reader como bean
    @Bean
    public CustomEmployeeReader reader(EmployeeDao dao) {
        CustomEmployeeReader r = new CustomEmployeeReader();
        r.setEmployeeDao(dao);
        return r;
    }

    // 2) Define el writer como bean
    @Bean
    public CustomEmployeeWriter writer(EmployeeDao dao) {
        CustomEmployeeWriter w = new CustomEmployeeWriter();
        w.setEmployeeDao(dao);
        return w;
    }

    // 3) El step inyecta ya reader y writer
    @Bean
    public Step step1(StepBuilderFactory steps,
                      CustomEmployeeReader reader,
                      CustomEmployeeWriter writer) {
        return steps.get("step1")
                    .<Employee, Employee>chunk(10)
                    .reader(reader)
                    .writer(writer)
                    .build();
    }

    @Bean
    public Job employeeJob(JobBuilderFactory jobs, Step step1) {
        return jobs.get("employeeJob")
                   .start(step1)
                   .build();
    }
}
