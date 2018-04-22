package com.project.manager.controllers.manager;

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
public class ManagerProjectViewController implements Initializable {

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
    public ManagerProjectViewController(TaskGenerator taskGenerator) {
        this.sessionService = SessionService.getInstance();
        this.taskGenerator = taskGenerator;
        this.sceneManager = SceneManager.getInstance();
        this.logger = Logger.getLogger(ManagerProjectViewController.class);
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
        injectTasksToVBoxes();
    }

    /**
     * Inject tasks of chosen project to specified VBoxes
     */
    private void injectTasksToVBoxes() {
        taskGenerator.inject(productBacklogVBox, TaskStatus.PRODUCT_BACKLOG);
        taskGenerator.inject(sprintBacklogVBox, TaskStatus.SPRINT_BACKLOG);
        taskGenerator.inject(inProgressVBox, TaskStatus.IN_PROGRESS);
        taskGenerator.inject(testingVBox, TaskStatus.TESTING);
        taskGenerator.inject(codeReviewVBox, TaskStatus.CODE_REVIEW);
        taskGenerator.inject(doneVBox, TaskStatus.DONE);
    }
}
