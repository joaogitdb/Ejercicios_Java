package com.SpringBatch.Ejercicio_1.config;

import com.SpringBatch.Ejercicio_1.model.Employee;
import com.SpringBatch.Ejercicio_1.processor.EmployeeValidatorProcessor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;

@Configuration		
public class JobConfig {

    // 1. Lector del fichero CSV
    @Bean
    public FlatFileItemReader<Employee> reader() {
        FlatFileItemReader<Employee> reader = new FlatFileItemReader<>();
        reader.setResource(new ClassPathResource("employees.csv"));
        reader.setLinesToSkip(0);
        reader.setLineMapper(new DefaultLineMapper<>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames("userName", "userId", "transactionDate", "transactionAmount");
                setDelimiter(",");
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<>() {{
                setTargetType(Employee.class);
            }});
        }});
        return reader;
    }

    // 2. Procesador de validación y fecha
    @Bean
    public ItemProcessor<Employee, Employee> processor() {
        return new EmployeeValidatorProcessor();
    }

    // 3. Writer a la base de datos
    @Bean
    public JdbcBatchItemWriter<Employee> writer(DataSource dataSource) {
        JdbcBatchItemWriter<Employee> writer = new JdbcBatchItemWriter<>();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        writer.setSql("INSERT INTO employee (user_name, user_id, transaction_date, transaction_amount) VALUES (:userName, :userId, :transactionDate, :transactionAmount)");
        writer.setDataSource(dataSource);
        return writer;
    }

    // 4. Step (lectura → proceso → escritura)
    @Bean
    public Step step1(StepBuilderFactory stepBuilderFactory,
                      FlatFileItemReader<Employee> reader,
                      ItemProcessor<Employee, Employee> processor,
                      JdbcBatchItemWriter<Employee> writer) {

        return stepBuilderFactory.get("step1")
                .<Employee, Employee>chunk(5)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .faultTolerant()
                .skipLimit(10)
                .skip(Exception.class)
                .build();
    }

    // 5. Job
    @Bean
    public Job importEmployeeJob(JobBuilderFactory jobBuilderFactory, Step step1) {
        return jobBuilderFactory.get("importEmployeeJob")
                .incrementer(new RunIdIncrementer())
                .flow(step1)
                .end()
                .build();
    }
}
