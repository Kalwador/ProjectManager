package com.project.manager.controllers.task;

import com.jfoenix.controls.*;
import com.project.manager.exceptions.EmptyUsernameException;
import com.project.manager.exceptions.user.UserAlreadyExistException;
import com.project.manager.exceptions.user.UserDoesNotExistException;
import com.project.manager.models.UserRole;
import com.project.manager.services.SessionService;
import com.project.manager.services.TaskService;
import com.project.manager.ui.AlertManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import lombok.Getter;
import org.controlsfx.control.textfield.TextFields;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by seba on 2018-05-27
 */
@Getter
@Component
public class TaskWindowController implements Initializable {

    private TaskService taskService;
    private SessionService sessionService;

    @FXML
    private Label status;
    @FXML
    private Label tag;
    @FXML
    private Label name;
    @FXML
    private Label priority;
    @FXML
    private Label deadLine;
    @FXML
    private Label errorAddExecutors;
    @FXML
    private JFXButton editButton;
    @FXML
    private Label description;
    @FXML
    private VBox executors;
    @FXML
    private Pane editPane;
    @FXML
    private Pane infoPane;
    @FXML
    private JFXTextField editTag;
    @FXML
    private JFXTextField editName;
    @FXML
    private JFXComboBox<String> editStatus;
    @FXML
    private JFXComboBox<String> editPriority;
    @FXML
    private JFXDatePicker editDeadLine;
    @FXML
    private JFXButton cancel;
    @FXML
    private HBox editExecutors;
    @FXML
    private JFXTextField executorName;
    @FXML
    private JFXButton addExecutor;
    @FXML
    private JFXButton accept;
    @FXML
    private JFXTextArea editDescription;

    private List<String> possibleMembers;

    @Autowired
    public TaskWindowController(TaskService taskService) {
        this.taskService = taskService;
        this.sessionService = SessionService.getInstance();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        generateView();
        setUpEditFunctionality();
        cancel.setOnAction(e -> closeModification());
    }

    private void generateView() {
        taskService.generateView(status, tag, name, priority, description, deadLine, executors, editButton);
    }

    private void setUpEditFunctionality() {
        if (sessionService.getUserModel().getRole() == UserRole.ADMIN) {
            editButton.setVisible(true);
            editButton.setOnAction(e -> {
                possibleMembers = taskService.getProjectMembers();
                TextFields.bindAutoCompletion(executorName, possibleMembers);
                taskService.prepareAndShowEditComponents(this);
            });
            addExecutor.setOnAction(e -> {
                try {
                    taskService.addExecutor(executorName.getText());
                    resetError(errorAddExecutors);
                } catch (UserDoesNotExistException ex) {
                    setErrors(errorAddExecutors, "This already added!");
                } catch (UserAlreadyExistException ex) {
                    setErrors(errorAddExecutors, "User does not exist!");
                }
            });
            accept.setOnAction(e -> {
                taskService.saveChanges(editStatus.getSelectionModel().getSelectedItem(), editTag.getText(),
                        editName.getText(), editPriority.getSelectionModel().getSelectedItem(), editDeadLine.getValue(),
                        editDescription.getText());
                generateView();
                accept.setVisible(false);
                cancel.setVisible(false);
                editButton.setVisible(true);
            });
        }
    }

    private void closeModification() {
        Alert alert = AlertManager.showConfirmationAlert("End modification",
                                                        "Do you want to close modification and lose all modified data?");
        if (alert.getResult().equals(ButtonType.OK)) {
            taskService.endModification(this);
        }
    }

    private void setErrors(Label error, String message) {
        error.setVisible(true);
        error.setText(message);
    }

    private void resetError(Label error) {
        error.setText("");
        error.setVisible(false);
    }
}
