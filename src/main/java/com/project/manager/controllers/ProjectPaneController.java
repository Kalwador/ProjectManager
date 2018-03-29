package com.project.manager.controllers;

import com.project.manager.sceneManager.SceneManager;
import com.project.manager.sceneManager.SceneType;
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
 * This class perform display project view after clicked view button on one of user projectsAsUser on dashboard.
 */
@Getter
@Setter
public class ProjectPaneController implements Initializable {

    private Long projectId;

    @FXML
    private Label projectName;

    @FXML
    private Button viewProject;

    /**
     * Initialization of project view after clicked view button
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        viewProject.setOnAction(e -> {
            SceneManager sceneManager = SceneManager.getInstance();
            sceneManager.showInNewWindow(SceneType.PROJECT_VIEW);
        });
    }
}
