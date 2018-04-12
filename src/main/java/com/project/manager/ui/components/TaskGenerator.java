package com.project.manager.ui.components;

import com.project.manager.controllers.manager.ManagerTaskBrickComponentController;
import com.project.manager.entities.Project;
import com.project.manager.models.TaskStatus;
import com.project.manager.services.SessionService;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Class generates TaskBricks (Panes) in VBoxes to show tasks in specified container.
 */
@Component
@Getter
public class TaskGenerator {
    private SessionService sessionService;

    @Autowired
    public TaskGenerator() {
        this.sessionService = SessionService.getInstance();
    }

    /**
     * Method generates and inject Panes created for every single task and wire them with their controller.
     *
     * @param box        container for tasks bricks
     * @param taskStatus filter for specified task group
     */
    public void inject(VBox box, TaskStatus taskStatus) {
        Project project = sessionService.getProject();
        project.getTasks().forEach(task -> {
            if (task.getTaskStatus() == taskStatus.ordinal()) {
                try {
                    ManagerTaskBrickComponentController managerTaskBrickComponentController = new ManagerTaskBrickComponentController();
                    managerTaskBrickComponentController.setTask(task);
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/manager/managerTaskBrickComponent.fxml"));
                    fxmlLoader.setController(managerTaskBrickComponentController);
                    AnchorPane taskBox = fxmlLoader.load();
                    box.getChildren().add(taskBox);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
