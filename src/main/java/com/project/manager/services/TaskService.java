package com.project.manager.services;

import com.project.manager.controllers.task.TaskWindowController;
import com.project.manager.entities.Task;
import com.project.manager.entities.UserModel;
import com.project.manager.exceptions.EmptyUsernameException;
import com.project.manager.exceptions.user.UserAlreadyExistException;
import com.project.manager.exceptions.user.UserDoesNotExistException;
import com.project.manager.models.UserRole;
import com.project.manager.models.task.TaskPriority;
import com.project.manager.models.task.TaskStatus;
import com.project.manager.repositories.TaskRepository;
import com.project.manager.repositories.UserRepository;
import com.project.manager.ui.components.MemberPaneGenerator;
import com.project.manager.ui.sceneManager.SceneManager;
import com.project.manager.ui.sceneManager.SceneType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 */
@Service
public class TaskService {

    private static Long taskId;
    private static Task task;
    private static Set<UserModel> executors;
    private VBox executorsBox;

    private TaskRepository taskRepository;
    private UserRepository userRepository;
    private SceneManager sceneManager;
    private SessionService sessionService;
    private MemberPaneGenerator memberPaneGenerator;

    @Autowired
    public TaskService(TaskRepository taskRepository, UserRepository userRepository, MemberPaneGenerator memberPaneGenerator) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.memberPaneGenerator = memberPaneGenerator;
        this.sceneManager = SceneManager.getInstance();
        this.sessionService = SessionService.getInstance();
    }

    public List<String> getProjectMembers() {
        return task.getProject().getMembers()
                .stream().map(UserModel::getUsername).collect(Collectors.toList());
    }

    public void deleteExecutor(Long executorId) {
        Optional<UserModel> userModel = userRepository.findById(executorId);
        if (userModel.isPresent() && task.getUsers().contains(userModel.get())) {
            executors.remove(userModel.get());
        }
        generateTaskExecutorsPane(executorsBox, executors, true);
    }

    public void addExecutor(String username) throws UserDoesNotExistException, UserAlreadyExistException {
        if (!username.isEmpty()) {
            Optional<UserModel> userModel = userRepository.findByUsername(username);
            if (userModel.isPresent()) {
                if (!executors.contains(userModel.get())) {
                    executors.add(userModel.get());
                    generateTaskExecutorsPane(executorsBox, executors, true);
                } else throw new UserAlreadyExistException();
            } else throw new UserDoesNotExistException();
        }
    }


    public void generateView(Label status, Label tag, Label name, Label priority, Label description, Label deadLine,
                             VBox executors, Button editButton) {
        status.setText(task.getTaskStatus().name());
        tag.setText(task.getTag());
        name.setText(task.getName());
        setPriority(priority);
        description.setText(task.getDescription());
        deadLine.setText(task.getDeadline().toString());
        if (sessionService.getUserModel().getRole() == UserRole.ADMIN)
            editButton.setVisible(true);
        generateTaskExecutorsPane(executors, task.getUsers(), false);
    }

    private void generateTaskExecutorsPane(VBox dataBox, Set<UserModel> executorsList, boolean showDeleteButtons) {
        this.executorsBox = dataBox;
        dataBox.getChildren().clear();
        if (!executorsList.isEmpty()) {
            memberPaneGenerator.createTaskExecutorsPanes(executorsBox, executorsList, showDeleteButtons);
        }
    }

    public void openTaskWindow() {
        sceneManager.showInNewWindow(SceneType.TASK_WINDOW);
    }

    private void setPriority(Label priority) {
        TaskPriority taskPriority = task.getTaskPriority();
        switch (taskPriority) {
            case HIGH:
                priority.setStyle("-fx-background-color: #fb2020");
                break;
            case MEDIUM:
                priority.setStyle("-fx-background-color: #ffbb11");
                break;
            case LOW:
                priority.setStyle("-fx-background-color: #15da3f");
                break;
        }
        priority.setText(taskPriority.name());
    }

    public void prepareAndShowEditComponents(TaskWindowController taskWindowController) {
        showEditComponents(taskWindowController);
        injectDataToEditComponents(taskWindowController);
    }

    private void injectDataToEditComponents(TaskWindowController taskWindowController) {
        taskWindowController.getEditStatus().getSelectionModel().select(task.getTaskStatus().name());
        taskWindowController.getEditTag().setText(task.getTag());
        taskWindowController.getEditName().setText(task.getName());
        taskWindowController.getEditPriority().getSelectionModel().select(task.getTaskPriority().name());
        taskWindowController.getEditDeadLine().setValue(task.getDeadline());
        taskWindowController.getEditDescription().setText(task.getDescription());

    }

    private void showEditComponents(TaskWindowController taskWindowController) {
        taskWindowController.getInfoPane().setVisible(false);
        taskWindowController.getEditPane().setVisible(true);

        taskWindowController.getEditPriority().getItems().add(TaskPriority.LOW.name());
        taskWindowController.getEditPriority().getItems().add(TaskPriority.MEDIUM.name());
        taskWindowController.getEditPriority().getItems().add(TaskPriority.HIGH.name());

        taskWindowController.getStatus().setVisible(false);
        taskWindowController.getEditStatus().setVisible(true);

        taskWindowController.getEditStatus().getItems().add(TaskStatus.SPRINT_BACKLOG.name());
        taskWindowController.getEditStatus().getItems().add(TaskStatus.PRODUCT_BACKLOG.name());
        taskWindowController.getEditStatus().getItems().add(TaskStatus.IN_PROGRESS.name());
        taskWindowController.getEditStatus().getItems().add(TaskStatus.TESTING.name());
        taskWindowController.getEditStatus().getItems().add(TaskStatus.CODE_REVIEW.name());
        taskWindowController.getEditStatus().getItems().add(TaskStatus.DONE.name());

        taskWindowController.getEditExecutors().setVisible(true);
        taskWindowController.getAccept().setVisible(true);
        taskWindowController.getCancel().setVisible(true);
        taskWindowController.getEditButton().setVisible(false);

        taskWindowController.getEditDescription().setVisible(true);
        taskWindowController.getDescription().setVisible(false);
        generateTaskExecutorsPane(taskWindowController.getExecutors(), executors,true);
    }

    public void setUpTask(Long taskId) {
        this.taskId = taskId;
        this.task = taskRepository.getOne(taskId);
        this.executors = task.getUsers();
    }

    public void endModification(TaskWindowController taskWindowController) {
        taskWindowController.getInfoPane().setVisible(true);
        taskWindowController.getEditPane().setVisible(false);

        taskWindowController.getEditExecutors().setVisible(false);
        taskWindowController.getAccept().setVisible(false);
        taskWindowController.getCancel().setVisible(false);
        taskWindowController.getEditButton().setVisible(true);

        taskWindowController.getEditDescription().setVisible(false);
        taskWindowController.getDescription().setVisible(true);
        generateTaskExecutorsPane(taskWindowController.getExecutors(), executors,false);
    }

    public void saveChanges(String status, String tag, String name, String priority, LocalDate deadLine, String description) {
        Task task = taskRepository.getOne(taskId);
        task = changeExecutors(task);
        task.setTaskStatus(TaskStatus.valueOf(status));
        task.setTag(tag);
        task.setName(name);
        task.setTaskPriority(TaskPriority.valueOf(priority));
        task.setDeadline(deadLine);
        task.setDescription(description);
        taskRepository.save(task);
    }

    private Task changeExecutors(Task task) {
        for (UserModel u: task.getUsers()) {
            if (!executors.contains(u)) {
                u.getTasks().remove(task);
            }
            userRepository.save(u);
        }
        task.setUsers(executors);
        return task;
    }
}
