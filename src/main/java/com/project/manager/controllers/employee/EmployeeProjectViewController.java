package com.project.manager.controllers.employee;

import com.jfoenix.controls.JFXButton;
import com.project.manager.models.TaskStatus;
import com.project.manager.services.SessionService;
import com.project.manager.ui.components.TaskGenerator;
import com.project.manager.ui.sceneManager.SceneManager;
import com.project.manager.ui.sceneManager.SceneType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.apache.log4j.Logger;
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

    private SessionService sessionService;
    private SceneManager sceneManager;
    private TaskGenerator taskGenerator;
    private final Logger logger;

    @Autowired
    public EmployeeProjectViewController(TaskGenerator taskGenerator) {
        this.sessionService = SessionService.getInstance();
        this.taskGenerator = taskGenerator;
        this.sceneManager = SceneManager.getInstance();
        this.logger = Logger.getLogger(EmployeeProjectViewController.class);
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
        injectTasksToVBoxes(sessionService.getUserModel().getId());
    }

    /**
     * Inject tasks of chosen project to specified VBoxes
     */
    private void injectTasksToVBoxes(Long userId) {
        taskGenerator.injectOnlyForOneUser(productBacklogVBox, TaskStatus.PRODUCT_BACKLOG, userId);
        taskGenerator.injectOnlyForOneUser(sprintBacklogVBox, TaskStatus.SPRINT_BACKLOG, userId);
        taskGenerator.injectOnlyForOneUser(inProgressVBox, TaskStatus.IN_PROGRESS, userId);
        taskGenerator.injectOnlyForOneUser(testingVBox, TaskStatus.TESTING, userId);
        taskGenerator.injectOnlyForOneUser(codeReviewVBox, TaskStatus.CODE_REVIEW, userId);
        taskGenerator.injectOnlyForOneUser(doneVBox, TaskStatus.DONE, userId);
    }
}