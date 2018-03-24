package com.project.manager.sceneManager;

import com.project.manager.sceneManager.scenes.*;
import com.project.manager.sceneManager.scenes.system.CustomScene;
import javafx.stage.Stage;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * Main class to switching scenes in program.
 * Contains all methods to changing scenes in primaryStage and opening new ones.
 * SceneManager is singleton, instance cen be accessible thought getInstance() method.
 */
@Service
public class SceneManager {
    private static SceneManager instance;
    private Stage primaryStage;
    private LoginScene loginScene;
    private RegistrationScene registrationScene;
    private DashboardScene dashboardScene;
    private ProjectViewScene projectViewScene;
    private ResetPasswdScene resetPasswdScene;
    private HashMap<Integer, CustomScene> scenes;

    /**
     * private constructor, access only through method getInstance
     */
    private SceneManager() {
    }

    /**
     * Provides instance of SceneManager
     * @return instance
     */
    public static SceneManager getInstance(){
        if(instance == null){
            instance = new SceneManager();
        }
        return instance;
    }

    /**
     * This method is used only once in application.
     * JavaFX framework provides primaryStage that is passed to SceneManager.
     * Also initialize method initializeScenes()
     * @param primaryStage primaryStage provided by JavaFX framework
     */
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
        initializeScenes();
    }

    /**
     * This method is used only once in application.
     * Initializes all scenes declared in application.
     */
    private void initializeScenes() {
        this.loginScene = new LoginScene(primaryStage);
        this.registrationScene = new RegistrationScene(primaryStage);
        this.dashboardScene = new DashboardScene(primaryStage);
        this.projectViewScene = new ProjectViewScene(primaryStage);
        this.resetPasswdScene = new ResetPasswdScene(primaryStage);

        scenes = new HashMap<Integer, CustomScene>() {
            {
                put(SceneType.LOGIN.getId(), loginScene);
                put(SceneType.REGISTRATION.getId(), registrationScene);
                put(SceneType.DASHBOARD.getId(), dashboardScene);
                put(SceneType.PROJECT_VIEW.getId(), projectViewScene);
                put(SceneType.RESETPASSWD.getId(), resetPasswdScene);
            }
        };
    }

    /**
     * Change scene passed in parameter in PrimaryStage to SceneType,
     * @param type SceneType to show
     */
    public void showScene(SceneType type) {
        scenes.get(type.getId()).show();
    }

    /**
     * Change scene passed in parameter by ID in PrimaryStage to SceneType,
     * @param sceneID SceneTypeID to show
     */
    public void showScene(int sceneID) {
        scenes.get(sceneID).show();
    }

    /**
     * Hide scene passed in parameter in PrimaryStage to SceneType, but not close it.
     * @param type SceneType to show
     */
    public void hideScene(SceneType type) {
        scenes.get(type.getId()).hide();
    }

    /**
     * Hide scene passed in parameter by ID in PrimaryStage to SceneType, but not close it.
     * @param sceneID SceneTypeID to hide
     */
    public void hideScene(int sceneID) {
        scenes.get(sceneID).hide();
    }

    /**
     * Close scene passed in parameter in PrimaryStage,
     * @param type SceneType to show
     */
    public void closeScene(SceneType type) {
        scenes.get(type.getId()).close();
    }

    /**
     * Close scene passed in parameter by ID in PrimaryStage,
     * @param sceneID SceneTypeID to show
     */
    public void closeScene(int sceneID) {
        scenes.get(sceneID).close();
    }

    /**
     * Change scene passed in parameter in NewStage to SceneType,
     * there can be only one NewStage.
     * @param type SceneType to show
     */
    public void showInNewWindow(SceneType type) {
        scenes.get(type.getId()).showInNewScene();
    }

    /**
     * Change scene passed in parameter by ID in NewStage to SceneType,
     * There can be only one NewStage.
     * @param sceneID SceneTypeID to show
     */
    public void showInNewWindow(int sceneID) {
        scenes.get(sceneID).showInNewScene();
    }

    /**
     * Hide scene passed in parameter in NewStage, but not close it.
     * There can be only one NewStage.
     * @param type SceneType to show
     */
    public void hideNewWindow(SceneType type) {
        scenes.get(type.getId()).hideNewScene();
    }

    /**
     * Hide scene passed in parameter by ID in NewStage, but not close it.
     * There can be only one NewStage.
     * @param sceneID SceneTypeID to show
     */
    public void hideNewWindow(int sceneID) {
        scenes.get(sceneID).hideNewScene();
    }

    /**
     * Close scene passed in parameter in NewStage.
     * There can be only one NewStage.
     * @param type SceneType to show
     */
    public void closeNewWindow(SceneType type) {
        scenes.get(type.getId()).closeNewScene();
    }

    /**
     * Close scene passed in parameter by ID in NewStage.
     * There can be only one NewStage.
     * "when u go so far, that u can't take step back"
     * @param sceneID SceneTypeID to show
     */
    public void closeNewWindow(int sceneID) {
        scenes.get(sceneID).closeNewScene();
    }
}


