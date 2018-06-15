package com.project.manager.controllers.employee;

import com.jfoenix.controls.JFXButton;
import com.project.manager.services.SessionService;
import com.project.manager.services.TaskGeneratorService;
import com.project.manager.ui.sceneManager.SceneManager;
import com.project.manager.ui.sceneManager.SceneType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * This is the class which is responsible for project view window.
 * This class perform display project information about selected project from dashboard
 */
@Component
public class EmployeeProjectViewController implements Initializable {

    @FXML
    private JFXButton backButton;
    @FXML
    private Label projectNameLabel;
    @FXML
    private VBox productBacklogVBox;
    @FXML
    private VBox sprintBacklogVBox;
    @FXML
    private VBox inProgressVBox;
    @FXML
    private VBox testingVBox;
    @FXML
    private VBox codeReviewVBox;
    @FXML
    private VBox doneVBox;
    @FXML
    private Button messages;
    @FXML
    private JFXButton userData;
    @FXML
    private JFXButton logout;

    private SessionService sessionService;
    private SceneManager sceneManager;
    private TaskGeneratorService taskGeneratorService;


    @Autowired
    public EmployeeProjectViewController(TaskGeneratorService taskGeneratorService) {
        this.sessionService = SessionService.getInstance();
        this.taskGeneratorService = taskGeneratorService;
        this.sceneManager = SceneManager.getInstance();
    }

    /**
     * Initialization of project view components
     *
     * @param location  standard JavaFX framework field
     * @param resources standard JavaFX framework field
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        projectNameLabel.setText(sessionService.getProject().getProjectName());
        backButton.setOnAction(e -> {
            sceneManager.showScene(SceneType.DASHBOARD);
        });
        taskGeneratorService.setUpReferences(productBacklogVBox, sprintBacklogVBox, inProgressVBox,
                testingVBox, codeReviewVBox, doneVBox);
        taskGeneratorService.injectTasksToVBoxesForUser(sessionService.getUserModel().getId());

        logout.setOnAction(e -> SessionService.getInstance().logoutUser());
        messages.setOnAction(e -> sceneManager.showInNewWindow(SceneType.MESSAGES_WINDOW));
        userData.setOnAction(e -> {
            sceneManager.showInNewWindow(SceneType.PERSONAL_DATA);
        });
    }
}