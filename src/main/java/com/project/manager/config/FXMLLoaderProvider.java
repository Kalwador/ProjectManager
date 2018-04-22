package com.project.manager.config;

import javafx.fxml.FXMLLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Class contain methods to load fxml files
 */
@Component
public class FXMLLoaderProvider {
    private AnnotationConfigApplicationContext context;

    /**
     * Method allows to load fxml files containing views
     * @param path path do fxml file
     * @return FXMLLoader containing view and bundle of resources
     */
    public FXMLLoader getLoader(String path) {
        FXMLLoader loader = new FXMLLoader();
        loader.setControllerFactory(param -> context.getBean(param));
        loader.setLocation(getClass().getResource(path));
        return loader;
    }

    /**
     *
     * @param context Context of application
     */
    @Autowired
    public void setContext(AnnotationConfigApplicationContext context) {
        this.context = context;
    }
}