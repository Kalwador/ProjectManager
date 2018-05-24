package com.project.manager.ui.components.admin;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.project.manager.controllers.admin.AdminDashboardController;
import com.project.manager.entities.Message;
import com.project.manager.entities.Project;
import com.project.manager.entities.UserModel;
import com.project.manager.exceptions.message.MessageNotExistException;
import com.project.manager.exceptions.project.ProjectNotExistException;
import com.project.manager.models.MessageTableView;
import com.project.manager.models.ProjectTableView;
import com.project.manager.models.UserTableView;
import com.project.manager.services.MessageService;
import com.project.manager.services.ProjectService;
import com.project.manager.services.user.UserService;
import com.project.manager.ui.AlertManager;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableRow;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * This class will manage the components of AdminDashboardController
 */
@Component
@Getter
public class AdminDashboardTablesComponent {

    /**
     * List of Projects in database
     */
    public static ObservableList<ProjectTableView> projectTableViews;
    /**
     * List of all users in database
     */
    public static ObservableList<UserTableView> userTableViews;

    private ProjectService projectService;
    private UserService userService;
    private AdminDashboardController adminDashboardController;
    private List<Long> selectedProjectIds;
    private List<Long> selectedUserIds;

    /**
     * This is the constructor with injected services and one controller
     *
     * @param projectService           injected project service which provides all project login methods
     * @param userService              injected user service which provides all project login methods
     * @param adminDashboardController injected admin dashboard controller to get reference to JavaFX components
     *                                 in admin view like tables and buttons
     */
    @Autowired
    public AdminDashboardTablesComponent(ProjectService projectService,
                                         UserService userService,
                                         @Lazy AdminDashboardController adminDashboardController) {
        this.projectService = projectService;
        this.userService = userService;
        this.adminDashboardController = adminDashboardController;
    }

    /**
     * This is method to generate table views in admin dashboard window depending on tab selection
     */
    public void generateTables() {
        if (adminDashboardController.getProjectsTab().isSelected() &&
                adminDashboardController.getProjectTable().getCurrentItemsCount() < 1) {
            generateProjectTableView();
        }
        if (adminDashboardController.getUsersTab().isSelected() &&
                adminDashboardController.getUserTable().getCurrentItemsCount() < 1) {
            generateUserTableView();
        }
    }

    /**
     * This method generate table view for all users in application and provides some button to do some action depending
     * on select row of user in table or just click on specific row of table
     * <p>
     * Inside is also refactor method to color users which are locked or block
     */
    public void generateUserTableView() {
        adminDashboardController.getUserTable().getColumns().clear();

        List<UserModel> users = userService.getAllUsers();
        if (!users.isEmpty()) {
            userTableViews = FXCollections.observableList(users.stream()
                    .map(UserTableView::convert)
                    .map(u -> u.generateDelButton(u))
                    .peek(u -> u.getDelete().getValue()
                        .setOnAction(e -> {
                            userService.delete(u.getId().get());
                            generateUserTableView();
                            generateProjectTableView();
                        }))
                    .map(u -> u.generateLockOrUnlockButton(u))
                    .peek(u -> u.getLockOrUnlock().getValue()
                        .setOnAction(e -> {
                            userService.changeLockStatus(u.getIsLocked().get(), u.getId().get());
                            generateUserTableView();
                        }))
                    .map(u -> u.generateResetButton(u))
                    .peek(u -> u.getResetPass().getValue()
                            .setOnAction(e -> userService.changePassword(u.getId().get())))
                    .peek(projectTableView -> {
                        projectTableView.getCheck().get().setOnAction(e -> {
                            disableUsersDeleteButton();
                        });
                    })
                    .collect(Collectors.toList()));

            adminDashboardController.getUserTable().setOnMouseClicked(e -> {
                int focusedIndex = adminDashboardController.getUserTable().getSelectionModel().getFocusedIndex();
                if (focusedIndex >= 0) {
                    userTableViews.get(focusedIndex).getCheck().get().fire();
                    adminDashboardController.getUserTable().getSelectionModel().clearSelection();
                }
            });

            TreeTableColumn<UserTableView, CheckBox> checkColumn = new TreeTableColumn<>("");
            TreeTableColumn<UserTableView, Long> idColumn = new TreeTableColumn<>("Id");
            TreeTableColumn<UserTableView, String> usernameColumn = new TreeTableColumn<>("Username");
            TreeTableColumn<UserTableView, String> emailColumn = new TreeTableColumn<>("Email");
            TreeTableColumn<UserTableView, String> roleColumn = new TreeTableColumn<>("Role");
            TreeTableColumn<UserTableView, Integer> countOfProjectsColumn = new TreeTableColumn<>("Count of projects");
            TreeTableColumn<UserTableView, Boolean> lockColumn = new TreeTableColumn<>("Lock");
            TreeTableColumn<UserTableView, JFXButton> lockButtonColumn = new TreeTableColumn<>("");
            TreeTableColumn<UserTableView, JFXButton> resetPassColumn = new TreeTableColumn<>("");
            TreeTableColumn<UserTableView, JFXButton> deleteColumn = new TreeTableColumn<>("");

            checkColumn.setSortable(false);
            idColumn.setSortable(false);
            usernameColumn.setSortable(false);
            emailColumn.setSortable(false);
            roleColumn.setSortable(false);
            countOfProjectsColumn.setSortable(false);
            lockColumn.setSortable(false);
            lockButtonColumn.setSortable(false);
            resetPassColumn.setSortable(false);
            deleteColumn.setSortable(false);


            adminDashboardController.getUserTable().getColumns().addAll
                    (checkColumn, idColumn, usernameColumn, emailColumn, roleColumn, countOfProjectsColumn, lockColumn, lockButtonColumn, resetPassColumn, deleteColumn);

            checkColumn.setCellValueFactory(u -> new SimpleObjectProperty(u.getValue().getValue().getCheck().get()));
            idColumn.setCellValueFactory(u -> u.getValue().getValue().getId().asObject());
            usernameColumn.setCellValueFactory(u -> u.getValue().getValue().getUsername());
            emailColumn.setCellValueFactory(u -> u.getValue().getValue().getEmail());
            roleColumn.setCellValueFactory(u -> u.getValue().getValue().getRole());
            countOfProjectsColumn.setCellValueFactory(u -> u.getValue().getValue().getCountOfProjects().asObject());
            lockColumn.setCellValueFactory(u -> u.getValue().getValue().getIsLocked());
            lockButtonColumn.setCellValueFactory(u -> new SimpleObjectProperty(u.getValue().getValue().getLockOrUnlock().get()));
            resetPassColumn.setCellValueFactory(u -> new SimpleObjectProperty(u.getValue().getValue().getResetPass().get()));
            deleteColumn.setCellValueFactory(u -> new SimpleObjectProperty(u.getValue().getValue().getDelete().get()));

            TreeItem<UserTableView> item = new RecursiveTreeItem<UserTableView>(userTableViews, RecursiveTreeObject::getChildren);

            adminDashboardController.getUserTable().setRoot(item);
            adminDashboardController.getUserTable().setShowRoot(false);
            adminDashboardController.getUserTable().setRowFactory(row -> new TreeTableRow<UserTableView>() {
                @Override
                protected void updateItem(UserTableView item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null) {
                        setStyle("-fx-background-color: #ff1a1a;-fx-text-fill:#ffffff");
                    } else {
                        if (!item.getIsLocked().get()) {
                            setStyle("-fx-background-color: #99ff99;-fx-text-fill:#000000");
                        }
                    }
                }
            });
        }
    }

    /**
     * This method get table of projects from Admin dashboard view and generate table with provided project form DB
     */
    public void generateProjectTableView() {
        adminDashboardController.getProjectTable().getColumns().clear();

        List<Project> projects = projectService.getAllProjects();
        if (!projects.isEmpty()) {
            projectTableViews = FXCollections
                    .observableList(projects
                            .stream()
                            .map(ProjectTableView::convert)
                            .map(projectTableView -> projectTableView.generateDelButton(projectTableView))
                            .peek(projectTableView -> projectTableView.getDelete().getValue()
                                .setOnAction(e -> {
                                    try {
                                        projectService.delete(projectTableView.getId().get());
                                        generateProjectTableView();
                                        adminDashboardController.getDeleteProject().setDisable(true);
                                        adminDashboardController.getShowProject().setDisable(true);
                                        adminDashboardController.getUpdateProject().setDisable(true);
                                    } catch (ProjectNotExistException ex) {
                                        AlertManager.showErrorAlert("Project not exist", "");
                                    }
                                }))
                            .peek(projectTableView -> {
                                projectTableView.getCheck().get().setOnAction(e -> {
                                    disableProjectsDeleteButton();
                                    disableProjectsUpdateAndShowButton();
                                });
                            })
                            .collect(Collectors.toList()));

            adminDashboardController.getProjectTable().setOnMouseClicked(e -> {
                int focusedIndex = adminDashboardController.getProjectTable().getSelectionModel().getFocusedIndex();
                if (focusedIndex >= 0) {
                    projectTableViews.get(adminDashboardController.getProjectTable().getSelectionModel().getFocusedIndex()).getCheck().get().fire();
                    adminDashboardController.getProjectTable().getSelectionModel().clearSelection();
                }
            });

            TreeTableColumn<ProjectTableView, CheckBox> checkColumn = new TreeTableColumn<>("");
            TreeTableColumn<ProjectTableView, Long> idColumn = new TreeTableColumn<>("Id");
            TreeTableColumn<ProjectTableView, String> projectNameColumn = new TreeTableColumn<>("Project name");
            TreeTableColumn<ProjectTableView, String> managerColumn = new TreeTableColumn<>("Manager");
            TreeTableColumn<ProjectTableView, String> clientColumn = new TreeTableColumn<>("Client");
            TreeTableColumn<ProjectTableView, Integer> countOfMembersColumn = new TreeTableColumn<>("Count of members");
            TreeTableColumn<ProjectTableView, JFXButton> deleteButtonColumn = new TreeTableColumn<>("");

            checkColumn.setSortable(false);
            idColumn.setSortable(false);
            projectNameColumn.setSortable(false);
            managerColumn.setSortable(false);
            countOfMembersColumn.setSortable(false);
            clientColumn.setSortable(false);
            deleteButtonColumn.setSortable(false);

            adminDashboardController.getProjectTable().getColumns().addAll
                    (checkColumn, idColumn, projectNameColumn, managerColumn, clientColumn, countOfMembersColumn, deleteButtonColumn);

            checkColumn.setCellValueFactory(p -> new SimpleObjectProperty(p.getValue().getValue().getCheck().get()));
            idColumn.setCellValueFactory(p -> p.getValue().getValue().getId().asObject());
            projectNameColumn.setCellValueFactory(p -> p.getValue().getValue().getProjectName());
            managerColumn.setCellValueFactory(p -> p.getValue().getValue().getManagerFirstAndLastName());
            clientColumn.setCellValueFactory(p -> p.getValue().getValue().getClientFirstAndLastName());
            countOfMembersColumn.setCellValueFactory(p -> p.getValue().getValue().getCountOfMembers().asObject());
            deleteButtonColumn.setCellValueFactory(p -> new SimpleObjectProperty(p.getValue().getValue().getDelete().get()));

            TreeItem<ProjectTableView> item = new RecursiveTreeItem<ProjectTableView>(projectTableViews, RecursiveTreeObject::getChildren);

            adminDashboardController.getProjectTable().setRoot(item);
            adminDashboardController.getProjectTable().setShowRoot(false);
        }
    }

    /**
     * This method perform disable delete button when anyone user is not selected
     * and enable button when anyone user is selected
     */
    private void disableUsersDeleteButton() {
        Optional result = userTableViews.stream()
                .filter(p -> p.getCheck().get().isSelected())
                .map(p -> p.getCheck().get().isSelected()).findAny();
        if (result.isPresent()) {
            adminDashboardController.getDeleteUsers().setDisable(false);
        } else {
            adminDashboardController.getDeleteUsers().setDisable(true);
        }
    }

    /**
     * This method perform disable delete button when anyone project is not selected
     * and enable button when anyone project is selected
     */
    private void disableProjectsDeleteButton() {
        Optional result = projectTableViews.stream()
                .filter(p -> p.getCheck().get().isSelected())
                .map(p -> p.getCheck().get().isSelected()).findAny();
        if (result.isPresent()) {
            adminDashboardController.getDeleteProject().setDisable(false);
        } else {
            adminDashboardController.getDeleteProject().setDisable(true);
        }
    }

    /**
     * This method perform disable delete update when anyone project is not selected
     * and enable button when only one project is selected
     */
    private void disableProjectsUpdateAndShowButton() {
        long onlyOneSelected = projectTableViews.stream()
                .filter(p -> p.getCheck().get().isSelected()).count();
        if (onlyOneSelected == 1) {
            adminDashboardController.getShowProject().setDisable(false);
            adminDashboardController.getUpdateProject().setDisable(false);

        } else {
            adminDashboardController.getShowProject().setDisable(true);
            adminDashboardController.getUpdateProject().setDisable(true);

        }
    }

    /**
     * This method perform deleting of selected users in users table in admin dashboard view
     */
    public void deleteSelectedUsers() {
        adminDashboardController.getDeleteUsers().setOnAction(e -> {
            selectedUserIds = userTableViews.stream()
                    .filter(u -> u.getCheck().get().isSelected())
                    .map(u -> u.getId().get()).collect(Collectors.toList());
            userService.delete(selectedUserIds);
            adminDashboardController.getDeleteUsers().setDisable(true);
            generateUserTableView();
            generateProjectTableView();
        });
    }

    /**
     * This method perform deleting of selected projects in projects table in admin dashboard view
     */
    public void deleteSelectedProjects() {
        adminDashboardController.getDeleteProject().setOnAction(e -> {
            selectedProjectIds = projectTableViews.stream()
                    .filter(u -> u.getCheck().get().isSelected())
                    .map(u -> u.getId().get()).collect(Collectors.toList());
            projectService.delete(selectedProjectIds);
            adminDashboardController.getDeleteProject().setDisable(true);
            generateProjectTableView();
            generateUserTableView();
        });
    }
}
