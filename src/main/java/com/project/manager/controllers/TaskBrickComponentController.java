package com.project.manager.controllers;

import com.project.manager.entities.Task;
import com.project.manager.models.task.TaskPriority;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * Controller of single task brick in VBox
 */
@Component
@Getter
@Setter
public class TaskBrickComponentController implements Initializable {
    @FXML
    private AnchorPane brickPane;
    @FXML
    private Pane priorityColor;
    @FXML
    private Label tagLabel;
    @FXML
    private Label nameLabel;
    @FXML
    private Label deadlineLabel;

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
        deadlineLabel.setText(task.getDeadline().toString());
        brickPane.setStyle("-fx-background-color: "+getHexFromColor(getColorByDeadline(task.getDeadline()))+";");
        priorityColor.setStyle("-fx-background-color: "+getHexFromColor(getColorByTaskPriority(task.getTaskPriority()))+";");
    }

    private Color getColorByTaskPriority(TaskPriority priority){
        if(priority == TaskPriority.LOW) return new Color(0, 255, 0,100);
        if(priority == TaskPriority.MEDIUM) return new Color(255, 255, 0, 100);
        if(priority == TaskPriority.HIGH) return new Color(255, 0, 0, 100);
        return new Color(255, 255, 255, 100);
    }

    private Color getColorByDeadline(LocalDate date){
        int daysLeft = date.getDayOfYear() - LocalDate.now().getDayOfYear();
        if(daysLeft > 8) return new Color(208, 255, 137,100);
        if(daysLeft > 5) return new Color(247, 149, 113, 100);
        if(daysLeft > 2) return new Color(255, 127, 104, 100);
        return new Color(255,99,71, 100);
    }
    private String getHexFromColor(Color color){
        return  "#"+Integer.toHexString(color.getRGB()).substring(2);
    }
}
