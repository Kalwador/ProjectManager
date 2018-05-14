package com.project.manager.controllers.admin;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableView;
import com.project.manager.models.MessageTableView;
import com.project.manager.models.ProjectTableView;
import com.project.manager.models.UserTableView;
import com.project.manager.services.ProjectService;
import com.project.manager.services.SessionService;
import com.project.manager.services.login.LoginService;
import com.project.manager.ui.components.admin.AdminDashboardTablesComponent;
import com.project.manager.ui.sceneManager.SceneManager;
import com.project.manager.ui.sceneManager.SceneType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TreeItem;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This is class which is responsible to handling the actions on AdminDashboard Windows, this class also
 * contains all references to tables and other JavaFX and JFeonix components which helps as to create interface
 */
@Component
@Getter
@Setter
public class AdminDashboardController implements Initializable {

    private AdminDashboardTablesComponent adminDashboardTablesComponent;
    private ProjectService projectService;
    private LoginService loginService;
    private SceneManager sceneManager;

    /**
     * The constructor of this Spring Bean contains reference to {@link AdminDashboardTablesComponent},
     * this class are injected to this controller to manage components of AdminDashboard Window
     * Inside constructor is created reference to SessionManager {@link SceneManager} to switch some other
     * windows
     *
     * @param adminDashboardTablesComponent
     */
    @Autowired
    public AdminDashboardController(AdminDashboardTablesComponent adminDashboardTablesComponent,
                                    ProjectService projectService,
                                    LoginService loginService) {
        this.adminDashboardTablesComponent = adminDashboardTablesComponent;
        this.projectService = projectService;
        this.loginService = loginService;
        this.sceneManager = SceneManager.getInstance();
    }

    @FXML
    private Tab projectsTab;

    @FXML
    private Tab usersTab;

    @FXML
    private Tab messageTab;

    @FXML
    private JFXTreeTableView<ProjectTableView> projectTable;

    @FXML
    private JFXTreeTableView<UserTableView> userTable;

    @FXML
    private JFXTreeTableView<MessageTableView> inboxTable;

    @FXML
    private JFXTreeTableView<MessageTableView> sentboxTable;

    @FXML
    private JFXButton logout;

    @FXML
    private JFXButton updateProject;

    @FXML
    private JFXButton deleteProject;

    @FXML
    private JFXButton showProject;
    @FXML
    private Button sentMessage;

    @FXML
    private JFXButton deleteUsers;

    @FXML
    private JFXButton deleteMessages;

    @FXML
    private Tab selectedInbox;

    @FXML
    private Tab selectedSendbox;

    /**
     * This method is responsible for listening the controller in window, and making action
     * implemented in lambdas expression
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sentMessage.setOnAction(e -> sceneManager.showInNewWindow(SceneType.MESSAGE_SENT_WINDOW));

        adminDashboardTablesComponent.generateProjectTableView();
        adminDashboardTablesComponent.deleteSelectedUsers();
        adminDashboardTablesComponent.deleteSelectedProjects();
        adminDashboardTablesComponent.deleteSelectedMessages();

        projectsTab.setOnSelectionChanged(e -> adminDashboardTablesComponent.generateTables());
        usersTab.setOnSelectionChanged(e -> adminDashboardTablesComponent.generateTables());
        messageTab.setOnSelectionChanged(e -> adminDashboardTablesComponent.generateTables());

        inboxTable.setOnMousePressed(e -> {
            TreeItem<MessageTableView> item = inboxTable.getSelectionModel().getSelectedItem();
            if (e.isPrimaryButtonDown() && e.getClickCount() == 2 && Optional.ofNullable(item).isPresent()) {
                adminDashboardTablesComponent.showMessageWindow(item.getValue().getId().get());
            }
        });

        sentboxTable.setOnMousePressed(e -> {
            TreeItem<MessageTableView> item = sentboxTable.getSelectionModel().getSelectedItem();
            if (e.isPrimaryButtonDown() && e.getClickCount() == 2 && Optional.ofNullable(item).isPresent()) {
                adminDashboardTablesComponent.showMessageWindow(item.getValue().getId().get());
            }
        });

        showProject.setOnAction(e -> sceneManager.showInNewWindow(SceneType.ADMIN_PROJECT_VIEW));

        updateProject.setOnAction(e -> {
            projectService.getProjectToUpdate();
            sceneManager.showInNewWindow(SceneType.ADMIN_UPDATE_PROJECT);
        });

        logout.setOnAction(e -> SessionService.getInstance().logoutUser());
    }
}