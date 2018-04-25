package com.project.manager.controllers.employee;

import com.project.manager.entities.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller of single task brick in VBox
 */
@Component
@Getter
@Setter
public class EmployeeTaskBrickComponentController implements Initializable {
    @FXML
    private AnchorPane brickPane;
    @FXML
    private Pane priorityColor;
    @FXML
    private Label tagLabel;
    @FXML
    private Label nameLabel;

    private Task task;


    /**
     * Standard method initialized on controller setup.
     * Defines action after click on task in VBox
     *
     * @param location  standard framework vaariable
     * @param resources standard framework vaariable
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nameLabel.setText(task.getName());
        tagLabel.setText(task.getTag());
        switch (task.getPriority()) {
            case 0: {
                priorityColor.setStyle("-fx-background-color: #00FF00;");
                break;
            }
            case 1: {
                priorityColor.setStyle("-fx-background-color: #ffff00;");
                break;
            }
            case 2: {
                priorityColor.setStyle("-fx-background-color: #ff0000;");
                break;
            }
        }
    }
}
