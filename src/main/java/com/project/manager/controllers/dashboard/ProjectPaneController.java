package com.project.manager.controllers.dashboard;

import com.project.manager.entities.Project;
import com.project.manager.services.SessionService;
import com.project.manager.ui.sceneManager.SceneManager;
import com.project.manager.ui.sceneManager.SceneType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import lombok.Getter;
import lombok.Setter;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * This is the class which is responsible for each pane view with project name.
 * This class perform display project view after clicked view button on one of user projects on dashboard.
 */
@Getter
@Setter
public class ProjectPaneController implements Initializable {
    @FXML
    private Label projectName;

    @FXML
    private Button viewProject;

    private Project project;
    private SessionService session;

    /**
     * Initialization of project view after clicked view button
     *
     * @param location  //TODO MACIEK - do uzupelnienia
     * @param resources //TODO MACIEK - do uzupelnienia
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        session = SessionService.getInstance();
        viewProject.setOnAction(e -> {
            SceneManager sceneManager = SceneManager.getInstance();
            session.setProject(project);

            if (project.getManager().getId().equals(session.getUserModel().getId())) {
                sceneManager.showScene(SceneType.MANAGER_PROJECT_VIEW);
            } else {
                sceneManager.showScene(SceneType.EMPLOYEE_PROJECT_VIEW);
            }
        });
    }
}
