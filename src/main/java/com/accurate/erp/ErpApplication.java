package com.accurate.erp;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
@EntityScan("com.accureate.erp.model.invoice")
@EnableJpaRepositories("com.accureate.erp.dao.invoice")
public class ErpApplication {
	
	private final static Logger LOGGER=LogManager.getLogger(ErpApplication.class);
	
	@Value("${DB_DRIVER}")
	   String DB_DRIVER;
	
	@Value("${DB_URL}")
	  String DB_URL;
	
	@Value("${DB_USERNAME}")
	  String DB_USERNAME;
	
	@Value("${DB_PASSWORD}")
	  String DB_PASSWORD;
	
	@Value("${hibernate_sql_dialect}")
	String hibernateDialect;

	public static void main(String[] args) {
		
		LOGGER.info("start of application--->");
		
		ConfigurableApplicationContext ctx=SpringApplication.run(ErpApplication.class, args);
		
		DataSource ds=ctx.getBean(DataSource.class);
		
		System.out.println(ds);
	}
	
	@Bean
	public DataSource dataSource() {
	 DriverManagerDataSource dataSource = new DriverManagerDataSource();
	 dataSource.setDriverClassName(DB_DRIVER);
	 dataSource.setUrl(DB_URL);
	 dataSource.setUsername(DB_USERNAME);
	 dataSource.setPassword(DB_PASSWORD);
	 return dataSource;
	}
	
	 @Bean(name = "entityManagerFactory")
	    public LocalSessionFactoryBean sessionFactory() {
	        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
	        sessionFactory.setDataSource(dataSource());
	        sessionFactory.setPackagesToScan("com.accurate.erp.*");
	        Properties hibernateProperties = new Properties();
	        hibernateProperties.put("hibernate.dialect", hibernateDialect);
	        hibernateProperties.put("hibernate.show_sql", "true");
	        hibernateProperties.put("hibernate.hbm2ddl.auto", "none");
	        sessionFactory.setHibernateProperties(hibernateProperties);
	 
	        return sessionFactory;
	    }
	 
	 


}
