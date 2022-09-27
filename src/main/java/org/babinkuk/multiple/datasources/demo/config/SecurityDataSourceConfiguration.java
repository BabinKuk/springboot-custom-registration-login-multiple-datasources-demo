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
import org.springframework.context.annotation.Primary;
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
		entityManagerFactoryRef = "securityEntityManagerFactory",
		transactionManagerRef = "securityTransactionManager",
		basePackages = {"org.babinkuk.multiple.datasources.demo.user.repository"})
public class SecurityDataSourceConfiguration {
	
	@Autowired
    private Environment env;
	
	@Primary
	@Bean(name = "securityDataSourceProperties")
	@ConfigurationProperties(prefix="spring.security-datasource")
	public DataSourceProperties securityDataSourceProperties() {
		return new DataSourceProperties();
	}
	
	@Primary
	@Bean(name = "securityDataSource")
    public DataSource securityDataSource() {
    	
		DataSourceProperties securityDataSourceProperties = securityDataSourceProperties();
		
		return DataSourceBuilder.create()
				.driverClassName(securityDataSourceProperties.getDriverClassName())
				.url(securityDataSourceProperties.getUrl())
				.username(securityDataSourceProperties.getUsername())
				.password(securityDataSourceProperties.getPassword())
				.build();
	}
    
	@Primary
	@Bean(name = "securityEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean securityEntityManagerFactory() {
    	
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setDataSource(securityDataSource());
		factory.setPackagesToScan(new String[]{"org.babinkuk.multiple.datasources.demo.user"});
		factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		
		Properties jpaProperties = new Properties();
		jpaProperties.put("hibernate.hbm2ddl.auto", env.getProperty("spring.jpa.hibernate.ddl-auto"));
		jpaProperties.put("hibernate.show-sql", env.getProperty("spring.jpa.show-sql"));
		factory.setJpaProperties(jpaProperties);
		
		return factory;
	}

	@Primary
	@Bean(name = "securityTransactionManager")
	public PlatformTransactionManager securityTransactionManager(
		@Qualifier("securityEntityManagerFactory") EntityManagerFactory securityEntityManagerFactory) {
		
		return new JpaTransactionManager(securityEntityManagerFactory);
	}

}
