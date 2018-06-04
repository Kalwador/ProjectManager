package com.project.manager.services.login;

import com.project.manager.entities.UserModel;
import com.project.manager.exceptions.AccountBlockedException;
import com.project.manager.exceptions.AccountLockedException;
import com.project.manager.services.RememberUserService;
import com.project.manager.ui.sceneManager.SceneManager;
import com.project.manager.ui.sceneManager.SceneType;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Class which is responsible for setting start screen depending on remember user
 */
@Service
public class LoginScreenManager {

    private LoginService loginService;
    private RememberUserService rememberUserService;
    private SceneManager sceneManager;

    @Autowired
    public LoginScreenManager(LoginService loginService, RememberUserService rememberUserService) {
        this.loginService = loginService;
        this.rememberUserService = rememberUserService;
        this.sceneManager = SceneManager.getInstance();
    }

    /**
     * This method are setting up the window on start of application
     *
     * @param primaryStage main stage of application
     */
    public void setLoginScreen(Stage primaryStage) {
        try {
            sceneManager.setPrimaryStage(primaryStage);
            Optional<UserModel> userModel = rememberUserService.getRememberedUser();
            boolean isUserRemembered = rememberUserService.isAnyRememberedUser();
            if (isUserRemembered && userModel.isPresent()) {
                loginService.loginRememberedUser();
            } else {
                sceneManager.showScene(SceneType.LOGIN);
            }
        } catch (AccountBlockedException | AccountLockedException ex) {
            sceneManager.showScene(SceneType.LOGIN);
        }
        primaryStage.show();
    }
}