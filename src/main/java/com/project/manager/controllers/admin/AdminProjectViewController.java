package com.project.manager.controllers.admin;

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
 * This is class which is responsible to handling the actions on AdminProjectView, where is
 * displayed content of selected project.
 */
@Component
public class AdminProjectViewController implements Initializable {

    @FXML
    private JFXButton backButton;
    @FXML
    private Button report;
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
    public AdminProjectViewController(TaskGeneratorService taskGeneratorService) {
        this.sessionService = SessionService.getInstance();
        this.sceneManager = SceneManager.getInstance();
        this.taskGeneratorService = taskGeneratorService;
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
        backButton.setOnAction(e -> sceneManager.showScene(SceneType.ADMIN_DASHBOARD));
        taskGeneratorService.injectTasksToVBoxes(productBacklogVBox, sprintBacklogVBox, inProgressVBox,
                testingVBox, codeReviewVBox, doneVBox);
        report.setOnAction(e -> sceneManager.showInNewWindow(SceneType.GENERATE_REPORT));

        logout.setOnAction(e -> SessionService.getInstance().logoutUser());
        messages.setOnAction(e -> sceneManager.showInNewWindow(SceneType.MESSAGES_WINDOW));
        userData.setOnAction(e -> {
            sceneManager.showInNewWindow(SceneType.PERSONAL_DATA);
        });
    }
}
