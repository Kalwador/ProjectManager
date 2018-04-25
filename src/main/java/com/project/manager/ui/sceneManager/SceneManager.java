package com.project.manager.ui.sceneManager;

import com.project.manager.ui.sceneManager.scenes.*;
import com.project.manager.ui.sceneManager.scenes.MessageViewWindowScene;
import com.project.manager.ui.sceneManager.scenes.system.CustomScene;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

import java.util.HashMap;

/**
 * Main class to switching scenes in program.
 * Contains all methods to changing scenes in primaryStage and opening new ones.
 * SceneManager is singleton, instance cen be accessible thought getInstance() method.
 */
public class SceneManager {
    private static SceneManager instance;
    private Stage primaryStage;
    private LoginScene loginScene;
    private RegistrationScene registrationScene;
    private DashboardScene dashboardScene;
    private ResetPasswordScene resetPasswordScene;
    private AdminDashboardScene adminDashboardScene;
    private UpdateProjectScene updateProjectScene;
    private MessageViewWindowScene messageViewWindowScene;
    private ManagerProjectView managerProjectView;
    private EmployeeProjectView employeeProjectView;
    private AddUserScene addUserScene;
    private MessageSentScene messageSentScene;
    private GenerateReportScene generateReportScene;
    private HashMap<Integer, CustomScene> scenes;
    private Logger logger;

    /**
     * private constructor, access only through method getInstance
     */
    private SceneManager() {
        this.logger = Logger.getLogger(SceneManager.class);
        this.logger.info("new instance of SceneManager created");
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
        logger.info("PrimaryStage set complete");
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
        this.updateProjectScene = new UpdateProjectScene(primaryStage);
        this.messageViewWindowScene = new MessageViewWindowScene(primaryStage);
        this.managerProjectView = new ManagerProjectView(primaryStage);
        this.employeeProjectView = new EmployeeProjectView(primaryStage);
        this.addUserScene = new AddUserScene(primaryStage);
        this.messageSentScene = new MessageSentScene(primaryStage);
        this.generateReportScene = new GenerateReportScene(primaryStage);

        scenes = new HashMap<Integer, CustomScene>() {
            {
                put(SceneType.LOGIN.ordinal(), loginScene);
                put(SceneType.REGISTRATION.ordinal(), registrationScene);
                put(SceneType.DASHBOARD.ordinal(), dashboardScene);
                put(SceneType.EMPLOYEE_PROJECT_VIEW.ordinal(), employeeProjectView);
                put(SceneType.MANAGER_PROJECT_VIEW.ordinal(), managerProjectView);
                put(SceneType.ADMIN_DASHBOARD.ordinal(), adminDashboardScene);
                put(SceneType.ADMIN_UPDATE_PROJECT.ordinal(), updateProjectScene);
                put(SceneType.MESSAGE_VIEW_WINDOW.ordinal(), messageViewWindowScene);
                put(SceneType.RESETPASSWD.ordinal(), resetPasswordScene);
                put(SceneType.ADD_USER.ordinal(), addUserScene);
                put(SceneType.MESSAGE_SENT_WINDOW.ordinal(), messageSentScene);
                put(SceneType.GENERATE_REPORT.ordinal(), generateReportScene);
            }
        };
        logger.info("Scenes initializec succesfully in initializeScenes method");
    }

    /**
     * Change scene passed in parameter in PrimaryStage to SceneType,
     *
     * @param type SceneType to show
     */
    public void showScene(SceneType type) {
        scenes.get(type.ordinal()).show();
        logger.trace("Show scene: " + type.toString());
    }

    /**
     * Change scene passed in parameter by ID in PrimaryStage to SceneType,
     *
     * @param sceneID SceneTypeID to show
     */
    public void showScene(int sceneID) {
        scenes.get(sceneID).show();
        logger.trace("Show scene: " + scenes.get(sceneID).toString());
    }

    /**
     * Hide scene passed in parameter in PrimaryStage to SceneType, but not close it.
     *
     * @param type SceneType to show
     */
    public void hideScene(SceneType type) {
        scenes.get(type.ordinal()).hide();
        logger.trace("Hide scene: " + type.toString());
    }

    /**
     * Hide scene passed in parameter by ID in PrimaryStage to SceneType, but not close it.
     *
     * @param sceneID SceneTypeID to hide
     */
    public void hideScene(int sceneID) {
        scenes.get(sceneID).hide();
        logger.trace("Hide scene: " + scenes.get(sceneID).toString());
    }

    /**
     * Close scene passed in parameter in PrimaryStage,
     *
     * @param type SceneType to show
     */
    public void closeScene(SceneType type) {
        scenes.get(type.ordinal()).close();
        logger.trace("Close scene: " + type.toString());
    }

    /**
     * Close scene passed in parameter by ID in PrimaryStage,
     *
     * @param sceneID SceneTypeID to show
     */
    public void closeScene(int sceneID) {
        scenes.get(sceneID).close();
        logger.trace("Close scene: " + scenes.get(sceneID).toString());
    }

    /**
     * Change scene passed in parameter in NewStage to SceneType,
     * there can be only one NewStage.
     *
     * @param type SceneType to show
     */
    public void showInNewWindow(SceneType type) {
        scenes.get(type.ordinal()).showInNewScene();
        logger.trace("Show scene in new window: " + type.toString());
    }

    /**
     * Change scene passed in parameter by ID in NewStage to SceneType,
     * There can be only one NewStage.
     *
     * @param sceneID SceneTypeID to show
     */
    public void showInNewWindow(int sceneID) {
        scenes.get(sceneID).showInNewScene();
        logger.trace("Show scene in new window: " + scenes.get(sceneID).toString());
    }

    /**
     * Hide scene passed in parameter in NewStage, but not close it.
     * There can be only one NewStage.
     *
     * @param type SceneType to show
     */
    public void hideNewWindow(SceneType type) {
        scenes.get(type.ordinal()).hideNewScene();
        logger.trace("Hide scene in new window: " + type.toString());
    }

    /**
     * Hide scene passed in parameter by ID in NewStage, but not close it.
     * There can be only one NewStage.
     *
     * @param sceneID SceneTypeID to show
     */
    public void hideNewWindow(int sceneID) {
        scenes.get(sceneID).hideNewScene();
        logger.trace("Hide scene in new window: " + scenes.get(sceneID).toString());
    }

    /**
     * Close scene passed in parameter in NewStage.
     * There can be only one NewStage.
     *
     * @param type SceneType to show
     */
    public void closeNewWindow(SceneType type) {
        scenes.get(type.ordinal()).closeNewScene();
        logger.trace("Close scene in new window: " + type.toString());
    }

    /**
     * Close scene passed in parameter by ID in NewStage.
     * There can be only one NewStage.
     * "when u go so far, that u can't take step back"
     *
     * @param sceneID SceneTypeID to show
     */
    public void closeNewWindow(int sceneID) {
        scenes.get(sceneID).closeNewScene();
        logger.trace("Close scene in new window: "+ scenes.get(sceneID).toString());
    }
}


