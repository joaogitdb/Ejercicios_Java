package com.SpringBatch.Ejercicio_1.config;

import com.SpringBatch.Ejercicio_1.model.Employee;
import com.SpringBatch.Ejercicio_1.processor.EmployeeItemProcessor;
import com.SpringBatch.Ejercicio_1.validator.EmployeeValidatorProcessor;
import com.SpringBatch.Ejercicio_1.writer.EmployeePreparedStatementSetter;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Autowired
    public BatchConfiguration(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
    }

    @Bean
    public FlatFileItemReader<Employee> reader() {
        return new FlatFileItemReaderBuilder<Employee>()
                .name("employeeItemReader")
                .resource(new ClassPathResource("employees.csv"))
                .delimited()
                .names("userName", "userId", "transactionDate", "transactionAmount")
                .fieldSetMapper(new BeanWrapperFieldSetMapper<>() {{
                    setTargetType(Employee.class);
                }})
                .build();
    }

    @Bean
    public ItemProcessor<Employee, Employee> processor() {
        return new EmployeeValidatorProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<Employee> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Employee>()
                .sql("INSERT INTO employee (user_name, user_id, transaction_date, transaction_amount) " +
                        "VALUES (?, ?, ?, ?)")
                .itemPreparedStatementSetter(new EmployeePreparedStatementSetter())
                .dataSource(dataSource)
                .build();
    }

    @Bean
    public DataSourceTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public Step step1(DataSource dataSource) {
        return stepBuilderFactory.get("step1")
                .<Employee, Employee>chunk(3)
                .reader(reader())
                .processor(processor())
                .writer(writer(dataSource))
                .transactionManager(transactionManager(dataSource))
                .build();
    }

    @Bean
    public Job importEmployeeJob(Step step1) {
        return jobBuilderFactory.get("importEmployeeJob")
                .incrementer(new RunIdIncrementer())
                .start(step1)
                .build();
    }
}