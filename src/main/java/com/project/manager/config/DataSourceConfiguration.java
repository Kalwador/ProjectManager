package com.project.manager.config;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Log4j
@Configuration
@ComponentScan(basePackages = "com.project.manager")
@EnableJpaRepositories(basePackages = "com.project.manager.repositories")
@PropertySource("classpath:application.properties")
public class DataSourceConfiguration {

    @Value("${db.driver}")
    private String PROPERTY_NAME_DATABASE_DRIVER;

    @Value("${db.url}")
    private String PROPERTY_NAME_DATABASE_URL;

    @Value("${db.username}")
    private String PROPERTY_NAME_DATABASE_USERNAME;

    @Value("${db.password}")
    private String PROPERTY_NAME_DATABASE_PASSWORD;

    @Value("${hibernate.dialect}")
    private String PROPERTY_NAME_HIBERNATE_DIALECT;

    @Value("${hibernate.show_sql}")
    private String PROPERTY_NAME_HIBERNATE_SHOW_SQL;

    private static final String PROPERTY_NAME_HIBERNATE_AUTO = "hibernate.hbm2ddl.auto";

    @Value("${packages.to.scan}")
    private String PROPERTY_NAME_PACKAGES_TO_SCAN;

    @Resource
    private Environment env;

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean factoryBean =
                new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(dataSource());
        factoryBean.setPackagesToScan(PROPERTY_NAME_PACKAGES_TO_SCAN);
        factoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        factoryBean.setJpaProperties(getJpaProperties());
        log.info("Bean entityManagerFactory created");
        return factoryBean;
    }

    @Bean
    public Properties getJpaProperties() {
        Properties properties = new Properties();
        properties.setProperty(PROPERTY_NAME_HIBERNATE_AUTO, env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_AUTO));
        properties.setProperty(PROPERTY_NAME_HIBERNATE_SHOW_SQL, PROPERTY_NAME_HIBERNATE_SHOW_SQL);
        properties.setProperty(PROPERTY_NAME_HIBERNATE_DIALECT, PROPERTY_NAME_HIBERNATE_DIALECT);
        log.info("Bean getJpaProperties created");
        return properties;
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource driver = new DriverManagerDataSource();
        driver.setDriverClassName(PROPERTY_NAME_DATABASE_DRIVER);
        driver.setUrl(PROPERTY_NAME_DATABASE_URL);
        driver.setUsername(PROPERTY_NAME_DATABASE_USERNAME);
        driver.setPassword(PROPERTY_NAME_DATABASE_PASSWORD);
        log.info("Bean dataSource created");
        return driver;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        log.info("Bean transactionManager created");
        return new JpaTransactionManager(entityManagerFactory);
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslator() {
        log.info("Bean exceptionTranslator created");
        return new PersistenceExceptionTranslationPostProcessor();
    }
}
