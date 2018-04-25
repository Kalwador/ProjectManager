package com.project.manager.config;

import lombok.extern.log4j.Log4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * Class provides Mailing System configuration form application properties
 */
@Log4j
@Configuration
@PropertySource("classpath:application.properties")
public class MailingSystemConfiguration {

    @Resource
    private Environment env;

    public static String EMAIL;
    public static String PASSWORD;
    public static String HOST;
    public static String START_TLS;
    public static String SSL_TRUST;
    public static String PORT;
    public static String AUTH;


    /**
     * Initialize configuration variables as address, PASSWORD, HOST
     */
    @PostConstruct
    public void setSystemVariables() {
        EMAIL = env.getRequiredProperty("email.address");
        PASSWORD = env.getRequiredProperty("email.password");
        HOST = env.getRequiredProperty("email.host");
        START_TLS = env.getRequiredProperty("mail.smtp.starttls.enable");
        SSL_TRUST = env.getRequiredProperty("mail.smtp.ssl.trust");
        PORT = env.getRequiredProperty("mail.smtp.port");
        AUTH = env.getRequiredProperty("mail.smtp.auth");
        log.info("Mailing system variables inicialized");
    }
}
