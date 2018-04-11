package com.project.manager.controllers.employee;

import com.jfoenix.controls.JFXButton;
import com.project.manager.ui.sceneManager.SceneManager;
import com.project.manager.ui.sceneManager.SceneType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
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
    private VBox productBacklogVBox;

    @FXML
    private JFXButton backButton;

    @FXML
    private Label projectNameLabel;

    @FXML
    private Button addUser;
    /**
     * Initialization of project view components
     *
     * @param location - URL location
     * @param resources - Bundle resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addUser.setOnAction(e -> {
            SceneManager sceneManager = SceneManager.getInstance();
            sceneManager.showInNewWindow(SceneType.ADD_USER);
        });
    }
}
