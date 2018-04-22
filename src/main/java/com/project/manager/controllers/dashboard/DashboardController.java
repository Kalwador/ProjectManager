package com.project.manager.controllers.dashboard;

import com.project.manager.ui.sceneManager.SceneManager;
import com.project.manager.ui.components.ProjectPaneGenerator;
import com.project.manager.ui.sceneManager.SceneType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import org.apache.log4j.Logger;
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
    private Button backToLoginButton;
    @FXML
    private VBox projectsArea;

    private SceneManager sceneManager;
    private ProjectPaneGenerator projectPaneGenerator;
    private final Logger logger;

    @Autowired
    public DashboardController(ProjectPaneGenerator projectPaneGenerator) {
        this.projectPaneGenerator = projectPaneGenerator;
        this.sceneManager = SceneManager.getInstance();
        this.logger = Logger.getLogger(DashboardController.class);
    }

    /**
     * Initialization of Dashboard view with project panes
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        projectPaneGenerator.createPanes(projectsArea);
        backToLoginButton.setOnAction(e -> sceneManager.showScene(SceneType.LOGIN));
    }
}
