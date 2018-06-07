package com.project.manager.controllers.task;

import com.jfoenix.controls.JFXButton;
import com.project.manager.config.ApplicationContextProvider;
import com.project.manager.controllers.admin.UpdateProjectController;
import com.project.manager.entities.UserModel;
import com.project.manager.services.SessionService;
import com.project.manager.services.TaskService;
import com.project.manager.services.user.UserService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import lombok.Getter;
import lombok.Setter;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Created by seba on 2018-05-27
 */
@Getter
@Setter
public class TaskExecutorPaneController implements Initializable {

    @FXML
    private JFXButton deleteExecutor;
    @FXML
    private Label executorName;
    @FXML
    private ImageView executorAvatar;

    private Long executorId;
    private TaskService taskService;


    public TaskExecutorPaneController() {
        this.taskService = ApplicationContextProvider.getInstance().getContext().getBean(TaskService.class);
    }

    /**
     * Initialization of actions on project members like add or delete member
     *
     * @param location  URL location
     * @param resources Bundle resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        deleteExecutor.setOnAction(e -> taskService.deleteExecutor(executorId));
    }
}
