package com.ejercicio.spring_batch_ejercicio2.config;

import javax.sql.DataSource;

import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;

import com.ejercicio.spring_batch_ejercicio2.CustomEmployeeReader;

@Configuration
@EnableBatchProcessing
public class BatchConfig extends DefaultBatchConfigurer {
	
	private DataSource ds;
	
	@Override
	public void setDataSource(DataSource dataSource) {}

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource drv = new DriverManagerDataSource();
		drv.setDriverClassName("com.mysql.cj.jbdc.Driver");
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
	
	
}
