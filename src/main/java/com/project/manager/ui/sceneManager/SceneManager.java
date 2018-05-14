package com.project.manager.ui.sceneManager;

import com.project.manager.ui.sceneManager.scenes.*;
import com.project.manager.ui.sceneManager.scenes.system.CustomScene;
import javafx.stage.Stage;
import lombok.extern.log4j.Log4j;

import java.util.HashMap;

/**
 * Main class to switching scenes in program.
 * Contains all methods to changing scenes in primaryStage and opening new ones.
 * SceneManager is singleton, instance cen be accessible thought getInstance() method.
 */
@Log4j
public class SceneManager {
    private static SceneManager instance;
    private Stage primaryStage;
    private LoginScene loginScene;
    private RegistrationScene registrationScene;
    private DashboardScene dashboardScene;
    private ResetPasswordScene resetPasswordScene;
    private AdminDashboardScene adminDashboardScene;
    private AdminProjectView adminProjectView;
    private UpdateProjectScene updateProjectScene;
    private MessageViewWindowScene messageViewWindowScene;
    private ManagerProjectView managerProjectView;
    private EmployeeProjectView employeeProjectView;
    private AddUserScene addUserScene;
    private MessageSentScene messageSentScene;
    private GenerateReportScene generateReportScene;
    private PersonalDataScene personalDataScene;

    private HashMap<Integer, CustomScene> scenes;

    /**
     * private constructor, access only through method getInstance
     */
    private SceneManager() {
        this.log.info("new instance of SceneManager created");
    }

    /**
     * Provides instance of SceneManager
     *
     * @return instance
     */
    public static SceneManager getInstance() {
        if (instance == null) {
            instance = new SceneManager();
        }
        return instance;
    }

    /**
     * This method is used only once in application.
     * JavaFX framework provides primaryStage that is passed to SceneManager.
     * Also initialize method initializeScenes()
     *
     * @param primaryStage primaryStage provided by JavaFX framework
     */
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
        log.info("PrimaryStage set complete");
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
        this.resetPasswordScene = new ResetPasswordScene(primaryStage);
        this.adminDashboardScene = new AdminDashboardScene(primaryStage);
        this.adminProjectView = new AdminProjectView(primaryStage);
        this.updateProjectScene = new UpdateProjectScene(primaryStage);
        this.messageViewWindowScene = new MessageViewWindowScene(primaryStage);
        this.managerProjectView = new ManagerProjectView(primaryStage);
        this.employeeProjectView = new EmployeeProjectView(primaryStage);
        this.addUserScene = new AddUserScene(primaryStage);
        this.messageSentScene = new MessageSentScene(primaryStage);
        this.generateReportScene = new GenerateReportScene(primaryStage);
        this.personalDataScene = new PersonalDataScene(primaryStage);

        scenes = new HashMap<Integer, CustomScene>() {
            {
                put(SceneType.LOGIN.ordinal(), loginScene);
                put(SceneType.REGISTRATION.ordinal(), registrationScene);
                put(SceneType.DASHBOARD.ordinal(), dashboardScene);
                put(SceneType.EMPLOYEE_PROJECT_VIEW.ordinal(), employeeProjectView);
                put(SceneType.MANAGER_PROJECT_VIEW.ordinal(), managerProjectView);
                put(SceneType.ADMIN_DASHBOARD.ordinal(), adminDashboardScene);
                put(SceneType.ADMIN_PROJECT_VIEW.ordinal(), adminProjectView);
                put(SceneType.ADMIN_UPDATE_PROJECT.ordinal(), updateProjectScene);
                put(SceneType.MESSAGE_VIEW_WINDOW.ordinal(), messageViewWindowScene);
                put(SceneType.RESET_PASSWORD.ordinal(), resetPasswordScene);
                put(SceneType.ADD_USER.ordinal(), addUserScene);
                put(SceneType.MESSAGE_SENT_WINDOW.ordinal(), messageSentScene);
                put(SceneType.GENERATE_REPORT.ordinal(), generateReportScene);
                put(SceneType.PERSONAL_DATA.ordinal(), personalDataScene);
            }
        };
        log.info("Scenes initializec succesfully in initializeScenes method");
    }

    /**
     * Change scene passed in parameter in PrimaryStage to SceneType,
     *
     * @param type SceneType to show
     */
    public void showScene(SceneType type) {
        scenes.get(type.ordinal()).show();
        log.trace("Show scene: " + type.toString());
    }

    /**
     * Change scene passed in parameter by ID in PrimaryStage to SceneType,
     *
     * @param sceneID SceneTypeID to show
     */
    public void showScene(int sceneID) {
        scenes.get(sceneID).show();
        log.trace("Show scene: " + scenes.get(sceneID).toString());
    }

    /**
     * Hide scene passed in parameter in PrimaryStage to SceneType, but not close it.
     *
     * @param type SceneType to show
     */
    public void hideScene(SceneType type) {
        scenes.get(type.ordinal()).hide();
        log.trace("Hide scene: " + type.toString());
    }

    /**
     * Hide scene passed in parameter by ID in PrimaryStage to SceneType, but not close it.
     *
     * @param sceneID SceneTypeID to hide
     */
    public void hideScene(int sceneID) {
        scenes.get(sceneID).hide();
        log.trace("Hide scene: " + scenes.get(sceneID).toString());
    }

    /**
     * Close scene passed in parameter in PrimaryStage,
     *
     * @param type SceneType to show
     */
    public void closeScene(SceneType type) {
        scenes.get(type.ordinal()).close();
        log.trace("Close scene: " + type.toString());
    }

    /**
     * Close scene passed in parameter by ID in PrimaryStage,
     *
     * @param sceneID SceneTypeID to show
     */
    public void closeScene(int sceneID) {
        scenes.get(sceneID).close();
        log.trace("Close scene: " + scenes.get(sceneID).toString());
    }

    /**
     * Change scene passed in parameter in NewStage to SceneType,
     * there can be only one NewStage.
     *
     * @param type SceneType to show
     */
    public void showInNewWindow(SceneType type) {
        scenes.get(type.ordinal()).showInNewScene();
        log.trace("Show scene in new window: " + type.toString());
    }

    /**
     * Change scene passed in parameter by ID in NewStage to SceneType,
     * There can be only one NewStage.
     *
     * @param sceneID SceneTypeID to show
     */
    public void showInNewWindow(int sceneID) {
        scenes.get(sceneID).showInNewScene();
        log.trace("Show scene in new window: " + scenes.get(sceneID).toString());
    }

    /**
     * Hide scene passed in parameter in NewStage, but not close it.
     * There can be only one NewStage.
     *
     * @param type SceneType to show
     */
    public void hideNewWindow(SceneType type) {
        scenes.get(type.ordinal()).hideNewScene();
        log.trace("Hide scene in new window: " + type.toString());
    }

    /**
     * Hide scene passed in parameter by ID in NewStage, but not close it.
     * There can be only one NewStage.
     *
     * @param sceneID SceneTypeID to show
     */
    public void hideNewWindow(int sceneID) {
        scenes.get(sceneID).hideNewScene();
        log.trace("Hide scene in new window: " + scenes.get(sceneID).toString());
    }

    /**
     * Close scene passed in parameter in NewStage.
     * There can be only one NewStage.
     *
     * @param type SceneType to show
     */
    public void closeNewWindow(SceneType type) {
        scenes.get(type.ordinal()).closeNewScene();
        log.trace("Close scene in new window: " + type.toString());
    }

    /**
     * Close scene passed in parameter by ID in NewStage.
     * There can be only one NewStage.
     *
     * @param sceneID SceneTypeID to show
     */
    public void closeNewWindow(int sceneID) {
        scenes.get(sceneID).closeNewScene();
        log.trace("Close scene in new window: "+ scenes.get(sceneID).toString());
    }
}


