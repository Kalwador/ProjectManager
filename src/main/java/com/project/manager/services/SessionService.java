package com.project.manager.services;

import com.project.manager.config.ApplicationContextProvider;
import com.project.manager.entities.Project;
import com.project.manager.entities.UserModel;
import com.project.manager.ui.sceneManager.SceneManager;
import com.project.manager.ui.sceneManager.SceneType;
import lombok.Getter;
import lombok.Setter;

/**
 * Singleton class type, contains data of current logged in user.
 */
@Getter
@Setter
public class SessionService {

    private static SessionService instance = null;
    private UserModel userModel;
    private Project project;
    private SceneManager sceneManager;

    /**
     * Method provides instance of Singleton class
     */
    private SessionService() {
        this.sceneManager = SceneManager.getInstance();
    }

    /**
     * Method provides Singleton instance of class
     *
     * @return singleton class instance
     */
    public static SessionService getInstance() {
        if (instance == null) {
            instance = new SessionService();
        }
        return instance;
    }

    /**
     * Method allows to log out of an application
     * Clears sessionService of user data and clear rememberedUser from file
     */
    public void logoutUser() {
        this.setUserModel(null);
        this.setProject(null);
        ApplicationContextProvider.getInstance().getContext()
                .getBean(RememberUserService.class)
                .deleteRememberedUser();
        sceneManager.hideAllNewScenes();
        sceneManager.showScene(SceneType.LOGIN);
    }
}