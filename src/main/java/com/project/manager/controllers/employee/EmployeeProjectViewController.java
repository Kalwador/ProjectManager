package com.project.manager.controllers.employee;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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

    /**
     * Initialization of project view components
     *
     * @param location - URL location
     * @param resources - Bundle resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
