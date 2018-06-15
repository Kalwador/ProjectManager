package com.project.manager.controllers.task;

import com.project.manager.entities.Task;
import com.project.manager.exceptions.*;
import com.project.manager.models.task.TaskPriority;
import com.project.manager.models.task.TaskStatus;
import com.project.manager.services.SessionService;
import com.project.manager.services.TaskGeneratorService;
import com.project.manager.services.TaskService;
import com.project.manager.ui.components.TaskGenerator;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Class responsible for validating and adding new task into project
 */
@Getter
@Setter
@Component

public class TaskAdderController implements Initializable {

    @FXML
    private TextField taskNameTextField;
    @FXML
    private TextField taskTagTextField;
    @FXML
    private TextField taskPriorityTextField;
    @FXML
    private Button addTaskButton;
    @FXML
    private Label labelName;
    @FXML
    private Label labelTag;
    @FXML
    private Label labelPriority;
    @FXML
    private Label descriptionLabel;
    @FXML
    private TextField taskDescriptionTextField;
    @FXML
    private ChoiceBox priorityChoiceBox;
    @FXML
    private Label labelDate;
    @FXML
    private DatePicker taskDatePicker;

    private Long executorId;
    private TaskService taskService;
    private static Task task;
    private SessionService sessionService;
    private TaskGeneratorService taskGeneratorService;

    @Autowired
    public TaskAdderController(TaskService taskService, TaskGeneratorService taskGeneratorService) {
        this.taskService = taskService;
        this.sessionService = SessionService.getInstance();
        this.taskGeneratorService = taskGeneratorService;
    }

    /**
     * Initialization of actions on project members like add or delete member
     *
     * @param location  URL location
     * @param resources Bundle resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resetErrors();
        this.setChoiceItems();
        this.validateDatePicker();
        addTaskButton.setOnAction(e -> {
            this.validateEntryData();
            taskService.refreshProject();
            taskGeneratorService.injectTasksToVBoxes();
        });
    }

    /**
     * Method populating choice box with possible task's priority options
     */
    private void setChoiceItems() {
        priorityChoiceBox.setItems(FXCollections.observableArrayList(TaskPriority.values()));
    }

    /**
     * Method blocking earlier than today's date in DatePicker
     */
    private void validateDatePicker() {
        taskDatePicker.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();

                setDisable(empty || date.compareTo(today) < 0 );
            }
        });
    }

    /**
     * Method used to validate input data and save task into database
     */
    private void validateEntryData() {
        this.resetErrors();
        try {
            String name = taskNameTextField.getText();
            String tag = taskTagTextField.getText();
            String priority = priorityChoiceBox.getSelectionModel().getSelectedItem().toString();
            String description = taskDescriptionTextField.getText();
            LocalDate taskDate = taskDatePicker.getValue();
            String stringTaskDateFormat = taskDate.toString();
            validateStrings(name, tag, priority, description, stringTaskDateFormat);
            TaskPriority taskPriority = TaskPriority.valueOf(priority);
            task = new Task();
            task.setName(name);
            task.setTag(tag);
            task.setDescription(description);
            task.setTaskPriority(taskPriority);
            task.setDeadline(taskDate);
            task.setTaskStatus(TaskStatus.PRODUCT_BACKLOG);
            task.setProject(sessionService.getProject());
            taskService.saveTask(task);
            Stage stage = (Stage) addTaskButton.getScene().getWindow();
            stage.close();
        } catch (EmptyTaskNameException etne) {
            labelName.setVisible(true);
            labelName.setText(etne.getMessage());
        } catch (EmptyTaskTagException ette) {
            labelTag.setVisible(true);
            labelTag.setText(ette.getMessage());
        } catch (EmptyTaskDescriptionException etde) {
            descriptionLabel.setVisible(true);
            descriptionLabel.setText(etde.getMessage());
        } catch (EmptyTaskPriorityException etpe) {
            labelPriority.setVisible(true);
            labelPriority.setText(etpe.getMessage());
        } catch (EmptyTaskDateException etde) {
            labelDate.setVisible(true);
            labelDate.setText(etde.getMessage());
        } catch (NullPointerException npe) {

        }
    }

    /**
     * Method validating emptiness of input strings
     * @param name task's name
     * @param tag tasks's tag
     * @param priority task's priority
     * @param description task's description
     * @param stringTaskDateFormat task's deadline date
     * @throws EmptyTaskNameException thrown when task's name is empty
     * @throws EmptyTaskTagException thrown when task's tag is empty
     * @throws EmptyTaskDescriptionException thrown when task's description is empty
     * @throws EmptyTaskPriorityException thrown when task's priority is empty
     * @throws EmptyTaskDateException thrown when task's deadline date is empty
     */
    private void validateStrings(String name, String tag, String priority, String description, String stringTaskDateFormat)
            throws EmptyTaskNameException, EmptyTaskTagException, EmptyTaskDescriptionException, EmptyTaskPriorityException, EmptyTaskDateException {
        try {
            if (name.isEmpty()) {
                throw new EmptyTaskNameException();
            }
            if (tag.isEmpty()) {
                throw new EmptyTaskTagException();
            }
            if (description.isEmpty()) {
                throw new EmptyTaskDescriptionException();
            }
            if (priority.isEmpty()) {
                throw new EmptyTaskPriorityException();
            }
            if (stringTaskDateFormat.isEmpty() || !Optional.ofNullable(taskDatePicker).isPresent()) {
                throw new EmptyTaskDateException();
            }
        } catch (NullPointerException npe) {

        }
    }

    /**
     * Method resetting all error labels
     */
    private void resetErrors() {
        labelDate.setText("");
        labelDate.setVisible(false);
        descriptionLabel.setText("");
        descriptionLabel.setVisible(false);
        labelPriority.setText("");
        labelPriority.setVisible(false);
        labelTag.setText("");
        labelTag.setVisible(false);
        labelName.setText("");
        labelName.setVisible(false);
    }
}