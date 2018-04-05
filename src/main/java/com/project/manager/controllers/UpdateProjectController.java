package com.project.manager.controllers;

import com.jfoenix.controls.JFXButton;
import com.project.manager.ui.components.admin.AdminDashboardTablesComponent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * This is the class which is responsible to manage the update project window,
 * that class include all necessary references to JavaFX components which will be use to send value to update the
 * selected project in future
 */
@Component
public class UpdateProjectController implements Initializable{

    @FXML
    private JFXButton accept;

    @FXML
    private JFXButton cancel;

    /**
     * This method is responsible for listening the controller in window, and making action
     * implemented in lambdas expression
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        accept.setOnAction(e -> AdminDashboardTablesComponent.projectTableViews
                .filtered(projectViewInTable -> projectViewInTable.getCheck().get().isSelected())
                .forEach(projectViewInTable -> System.out.println(projectViewInTable.getId().get())));

    }
}
