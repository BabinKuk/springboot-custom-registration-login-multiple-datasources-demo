package org.babinkuk.multiple.datasources.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class AppConfig /*extends WebMvcConfigurerAdapter*/ {
	
	@Bean
	public OpenEntityManagerInViewFilter securityOpenEntityManagerInViewFilter() {
		OpenEntityManagerInViewFilter osivFilter = new OpenEntityManagerInViewFilter();
		osivFilter.setEntityManagerFactoryBeanName("securityEntityManagerFactory");
		return osivFilter;
	}
	
	@Bean
	public OpenEntityManagerInViewFilter appDataOpenEntityManagerInViewFilter() {
		OpenEntityManagerInViewFilter osivFilter = new OpenEntityManagerInViewFilter();
		osivFilter.setEntityManagerFactoryBeanName("appDataEntityManagerFactory");
		return osivFilter;
	}

//	public AppConfig() {
//		// TODO Auto-generated constructor stub
//	}

}
