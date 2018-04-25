package com.project.manager;

import com.project.manager.config.ApplicationContextProvider;
import com.project.manager.services.login.LoginScreenManager;
import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.project.manager")
public class Main extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        ApplicationContextProvider.getInstance().getContext()
                .getBean(LoginScreenManager.class).setLoginScreen(primaryStage);
    }
}
