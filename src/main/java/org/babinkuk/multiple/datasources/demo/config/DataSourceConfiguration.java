package org.babinkuk.multiple.datasources.demo.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
		entityManagerFactoryRef = "appDataEntityManagerFactory",
		transactionManagerRef = "appDataTransactionManager",
				basePackages = {"org.babinkuk.multiple.datasources.demo.employee.repository"})
public class DataSourceConfiguration {
	
	@Autowired
	private Environment env;

	@Bean(name = "appDataSourceProperties")
	@ConfigurationProperties("spring.datasource")
	public DataSourceProperties appDataSourceProperties() {
		return new DataSourceProperties();
	}
	
	@Bean(name = "appDataSource")
    public DataSource appDataSource() {
    	DataSourceProperties appDataSourceProperties = appDataSourceProperties();
		
		return DataSourceBuilder.create()
				.driverClassName(appDataSourceProperties.getDriverClassName())
				.url(appDataSourceProperties.getUrl())
				.username(appDataSourceProperties.getUsername())
				.password(appDataSourceProperties.getPassword())
				.build();
    }
	
	@Bean(name = "appDataEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean appDataEntityManagerFactory() {
		
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setDataSource(appDataSource());
		factory.setPackagesToScan(new String[]{"org.babinkuk.multiple.datasources.demo.employee"});
		factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		
		Properties jpaProperties = new Properties();
		jpaProperties.put("hibernate.hbm2ddl.auto", env.getProperty("spring.jpa.hibernate.ddl-auto"));
		jpaProperties.put("hibernate.show-sql", env.getProperty("spring.jpa.show-sql"));
		factory.setJpaProperties(jpaProperties);
		
		return factory;
	}

	@Bean(name = "appDataTransactionManager")
	public PlatformTransactionManager appDataTransactionManager(
		@Qualifier("appDataEntityManagerFactory") EntityManagerFactory appDataEntityManagerFactory) {
		
		return new JpaTransactionManager(appDataEntityManagerFactory);
	}

}
