package com.project.manager.config;

import lombok.Getter;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Singleton class provides AnnotationConfigApplicationContext for SceneManager
 */
@Getter
public class ApplicationContextProvider {
    private static ApplicationContextProvider instance;
    private AnnotationConfigApplicationContext context;

    /**
     * Singleton getInstance method
     *
     * @return instance of class
     */
    public static ApplicationContextProvider getInstance() {
        if (instance == null) {
            instance = new ApplicationContextProvider();
        }
        return instance;
    }

    /**
     * Method initialize context field with ApplicationContext
     */
    private ApplicationContextProvider() {
        this.context = new AnnotationConfigApplicationContext(DataSourceConfiguration.class);
    }
}
