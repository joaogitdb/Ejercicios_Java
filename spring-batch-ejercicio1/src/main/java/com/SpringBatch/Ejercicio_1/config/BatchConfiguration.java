package com.SpringBatch.Ejercicio_1.config;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import com.SpringBatch.Ejercicio_1.listener.EmployeeSkipListener;
import com.SpringBatch.Ejercicio_1.model.Employee;
import com.SpringBatch.Ejercicio_1.validator.EmployeeValidatorProcessor;
import com.SpringBatch.Ejercicio_1.writer.EmployeePreparedStatementSetter;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

	 @Autowired
	    private JobBuilderFactory jobBuilderFactory;

	    @Autowired
	    private StepBuilderFactory stepBuilderFactory;
	
    @Bean
    public DataSource dataSource() {
    			// Cambiamos el DataSource para usar MySQL
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
        ds.setUrl("jdbc:mysql://localhost:3306/test");
        ds.setUsername("root");
        ds.setPassword("root12345");
        return ds;
    }

    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public FlatFileItemReader<Employee> reader() {
    			// Cambiamos el reader para usar el DataSource
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
    			// Cambiamos el processor para usar el EmployeeValidatorProcessor
        return new EmployeeValidatorProcessor();
    }
    
    @Bean
    public EmployeeSkipListener skipListener() {
    			// Cambiamos el listener para usar el EmployeeSkipListener
        return new EmployeeSkipListener();
    }

    @Bean
    public JdbcBatchItemWriter<Employee> writer(DataSource dataSource) {
    			// Cambiamos el writer para usar el DataSource
        return new JdbcBatchItemWriterBuilder<Employee>()
                .sql("INSERT INTO employee (user_name, user_id, transaction_date, transaction_amount) VALUES (?, ?, ?, ?)")
                .itemPreparedStatementSetter(new EmployeePreparedStatementSetter())
                .dataSource(dataSource)
                .build();
    }

    @Bean
    public Step step1(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
    			// Cambiamos el step para usar el DataSource
        return stepBuilderFactory.get("step1") // Cambiamos el nombre del step a "step1"
                .<Employee, Employee>chunk(3) // Cambiamos el tamaño del chunk a 3
                .reader(reader()) // Cambiamos el reader para usar el DataSource
                .processor(processor()) // Cambiamos el processor para usar el validator
                .writer(writer(dataSource())) // Cambiamos el writer para usar el DataSource
                .faultTolerant() // Habilitamos el manejo de errores
                .skip(Exception.class) // Aquí puedes especificar el tipo de excepción que quieres manejar	
                .skipLimit(100) // Para ajustar el valor si esperamos más o menos errores
                .listener(skipListener()) // Añadiremos esto abajo para pasar el listener
                .transactionManager(transactionManager) // Añadimos el transactionManager
                .repository(jobRepository) // Añadimos el jobRepository
                .build(); // Añadimos el jobRepository
    }
    
  

    @Bean
    public Job importEmployeeJob(JobBuilderFactory jobBuilderFactory, Step step1) {
    			// Cambiamos el job para usar el DataSource
        return jobBuilderFactory.get("importEmployeeJob")
                .incrementer(new RunIdIncrementer())
                .start(step1)
                .build();
    }
}