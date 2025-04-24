package com.ejercicio.spring_batch_ejercicio2.config;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.ejercicio.spring_batch_ejercicio2.CustomEmployeeReader;
import com.ejercicio.spring_batch_ejercicio2.CustomEmployeeWriter;
import com.ejercicio.spring_batch_ejercicio2.EmployeeProcessor;
import com.ejercicio.spring_batch_ejercicio2.Employee;

@Configuration
@EnableBatchProcessing
public class BatchConfig extends DefaultBatchConfigurer {

    private DataSource ds;

    // Sobrescribimos setDataSource para usar MapJobRepository (igual que MapJobRepositoryFactoryBean del XML)
    @Override
    public void setDataSource(DataSource dataSource) {
        // no invocamos super.setDataSource(...) => queda el repositorio en memoria
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource drv = new DriverManagerDataSource();
        drv.setDriverClassName("com.mysql.cj.jdbc.Driver");
        drv.setUrl("jdbc:mysql://localhost:3306/test");
        drv.setUsername("root");
        drv.setPassword("root12345");
        this.ds = drv;
        return drv;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(this.ds);
    }

    @Bean
    public CustomEmployeeReader customEmployeeReader() {
        CustomEmployeeReader reader = new CustomEmployeeReader();
        reader.setDataSource(this.ds);
        return reader;
    }

    @Bean
    public EmployeeProcessor employeeProcessor() {
        return new EmployeeProcessor();
    }

    @Bean
    public CustomEmployeeWriter customEmployeeWriter() {
        CustomEmployeeWriter writer = new CustomEmployeeWriter();
        writer.setDataSource(this.ds);
        return writer;
    }

    @Bean
    public Step step1(
            StepBuilderFactory steps,
            CustomEmployeeReader reader,
            EmployeeProcessor processor,
            CustomEmployeeWriter writer) {
        return steps.get("step1")
                    .<Employee, Employee>chunk(1)
                    .reader(reader)
                    .processor(processor)
                    .writer(writer)
                    .build();
    }

    @Bean
    public Job employeeJob(
            JobBuilderFactory jobs,
            Step step1) {
        return jobs.get("employeeJob")
                   .start(step1)
                   .build();
    }
}