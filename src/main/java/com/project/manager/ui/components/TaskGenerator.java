package com.project.manager.ui.components;

import com.project.manager.controllers.manager.ManagerTaskBrickComponentController;
import com.project.manager.entities.Project;
import com.project.manager.entities.Task;
import com.project.manager.models.task.TaskStatus;
import com.project.manager.services.SessionService;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import lombok.Getter;
import org.apache.log4j.Logger;
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
    private Logger logger;

    @Autowired
    public TaskGenerator() {
        this.sessionService = SessionService.getInstance();
        this.logger = Logger.getLogger(TaskGenerator.class);
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
                if (task.getUser().getId().equals(userId))
                    box.getChildren().add(getPaneFromTask(task));
            }
        });
    }

    private AnchorPane getPaneFromTask(Task task) {
        try {
            ManagerTaskBrickComponentController managerTaskBrickComponentController = new ManagerTaskBrickComponentController();
            managerTaskBrickComponentController.setTask(task);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/manager/managerTaskBrickComponent.fxml"));
            fxmlLoader.setController(managerTaskBrickComponentController);
            logger.info("Pane from task created succesfully " + task.toString());
            return fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("IOException in getPaneFromTask method during creating Pane from Task");
            throw new RuntimeException("IOException in getPaneFromTask method during creating Pane from Task");
        }
    }
}
