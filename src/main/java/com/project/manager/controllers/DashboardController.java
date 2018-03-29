package com.project.manager.controllers;

import com.project.manager.sceneManager.SceneManager;
import com.project.manager.sceneManager.SceneType;
import com.project.manager.ui.components.ProjectPaneGenerator;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * This is the class which is responsible for dashboard window.
 * This class perform display dynamically generated view projectsAsUser of logged user.
 */
@Component
public class DashboardController implements Initializable {

    @FXML
    private Button backToLogin;

    @FXML
    private VBox projectsArea;

    private SceneManager sceneManager;

    private ProjectPaneGenerator projectPaneGenerator;

    @Autowired
    public DashboardController(ProjectPaneGenerator projectPaneGenerator) {
       this.projectPaneGenerator = projectPaneGenerator;
       this.sceneManager = SceneManager.getInstance();
    }

    /**
     * Initialization of Dashboard view with project panes
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        projectPaneGenerator.createPanes(projectsArea);

        backToLogin.setOnAction(e -> {
            sceneManager.showScene(SceneType.LOGIN);
        });

    }
}
