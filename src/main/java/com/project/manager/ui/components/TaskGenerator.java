package com.project.manager.ui.components;

import com.project.manager.controllers.TaskBrickComponentController;
import com.project.manager.entities.Project;
import com.project.manager.entities.Task;
import com.project.manager.models.task.TaskStatus;
import com.project.manager.services.SessionService;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import lombok.Getter;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Class generates TaskBricks (Panes) in VBoxes to show tasks in specified container.
 */
@Log4j
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
                box.getChildren().add(getPaneFromTask(task));
            }
        });
    }

    public void injectOnlyForOneUser(VBox box, TaskStatus taskStatus, Long userId) {
        Project project = sessionService.getProject();
        project.getTasks().forEach(task -> {
            if (task.getTaskStatus() == taskStatus.ordinal()) {
                task.getUsers().forEach(userModel -> {
                    if (userModel.getId().equals(userId)) {
                        box.getChildren().add(getPaneFromTask(task));
                    }
                });
            }
        });
    }

    private AnchorPane getPaneFromTask(Task task) {
        try {
            TaskBrickComponentController taskBrickComponentController = new TaskBrickComponentController();
            taskBrickComponentController.setTask(task);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/TaskBrickComponent.fxml"));
            fxmlLoader.setController(taskBrickComponentController);
            log.info("Pane from task created succesfully " + task.toString());
            return fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
            log.error("IOException in getPaneFromTask method during creating Pane from Task");
            throw new RuntimeException("IOException in getPaneFromTask method during creating Pane from Task");
        }
    }
}
