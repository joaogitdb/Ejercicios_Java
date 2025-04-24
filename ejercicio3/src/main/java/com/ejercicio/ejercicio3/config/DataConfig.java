package com.ejercicio.ejercicio3.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.ejercicio.ejercicio3.repository")
@ComponentScan(basePackages = "com.ejercicio.ejercicio3")
public class DataConfig {

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource ds = new DriverManagerDataSource();
		ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
		ds.setUrl("jdbc:mysql://localhost:3306/ejercicio3");
		ds.setUsername("root");
		ds.setPassword("root12345");
		 return ds;
	}
	
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
		
		LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
		emf.setDataSource(dataSource);
		emf.setPackagesToScan("com.ejercicio.ejercicio3.entities");
		emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		emf.setJpaProperties(hibernateProperties());
		return emf;
	}
	
	private Properties hibernateProperties() {

		Properties props = new Properties();
        props.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
        props.setProperty("hibernate.hbm2ddl.auto", "update");
        return props;
	}
	
	 @Bean
	    public JpaTransactionManager transactionManager(EntityManagerFactory emf) {
	        return new JpaTransactionManager(emf);
	    }

}
