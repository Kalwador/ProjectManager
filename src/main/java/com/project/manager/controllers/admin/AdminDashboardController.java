package com.project.manager.controllers.admin;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableView;
import com.project.manager.models.ProjectTableView;
import com.project.manager.models.UserTableView;
import com.project.manager.services.ProjectService;
import com.project.manager.services.SessionService;
import com.project.manager.services.login.LoginService;
import com.project.manager.ui.components.MemberPaneGenerator;
import com.project.manager.ui.components.admin.AdminDashboardTablesComponent;
import com.project.manager.ui.sceneManager.SceneManager;
import com.project.manager.ui.sceneManager.SceneType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * This is class which is responsible to handling the actions on AdminDashboard Windows, this class also
 * contains all references to tables and other JavaFX and JFeonix components which helps as to create interface
 */
@Component
@Getter
@Setter
public class AdminDashboardController implements Initializable {

    @FXML
    private JFXButton userData;
    @FXML
    private Tab projectsTab;
    @FXML
    private Tab usersTab;
    @FXML
    private JFXTreeTableView<ProjectTableView> projectTable;
    @FXML
    private JFXTreeTableView<UserTableView> userTable;
    @FXML
    private JFXButton logout;
    @FXML
    private JFXButton messages;
    @FXML
    private JFXButton updateProject;
    @FXML
    private JFXButton deleteProject;
    @FXML
    private JFXButton showProject;
    @FXML
    private JFXButton addNewProject;
    @FXML
    private Button sentMessage;
    @FXML
    private JFXButton deleteUsers;

    private AdminDashboardTablesComponent adminDashboardTablesComponent;
    private ProjectService projectService;
    private LoginService loginService;
    private SceneManager sceneManager;
    private SessionService sessionService;
    private MemberPaneGenerator memberPaneGenerator;

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
                                    LoginService loginService,
                                    MemberPaneGenerator memberPaneGenerator) {
        this.adminDashboardTablesComponent = adminDashboardTablesComponent;
        this.projectService = projectService;
        this.loginService = loginService;
        this.memberPaneGenerator = memberPaneGenerator;
        this.sceneManager = SceneManager.getInstance();
        this.sessionService = SessionService.getInstance();
    }

    /**
     * This method is responsible for listening the controller in window, and making action
     * implemented in lambdas expression
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        adminDashboardTablesComponent.generateProjectTableView();
        adminDashboardTablesComponent.deleteSelectedUsers();
        adminDashboardTablesComponent.deleteSelectedProjects();

        projectsTab.setOnSelectionChanged(e -> adminDashboardTablesComponent.generateTables());
        usersTab.setOnSelectionChanged(e -> adminDashboardTablesComponent.generateTables());

        showProject.setOnAction(e -> {
            projectService.getProjectToShow();
            sceneManager.showInNewWindow(SceneType.ADMIN_PROJECT_VIEW);
        });

        updateProject.setOnAction(e -> {
            memberPaneGenerator.getMembers().clear();
            sessionService.setProject(null);
            projectService.getProjectToUpdate();
            sceneManager.showInNewWindow(SceneType.ADMIN_UPDATE_PROJECT);
        });

        addNewProject.setOnAction(e -> {
            memberPaneGenerator.getMembers().clear();
            sessionService.setProject(null);
            sceneManager.showInNewWindow(SceneType.ADMIN_CREATE_NEW_PROJECT);
        });

        logout.setOnAction(e -> SessionService.getInstance().logoutUser());

        messages.setOnAction(e -> sceneManager.showInNewWindow(SceneType.MESSAGES_WINDOW));

        userData.setOnAction(e -> {
            sceneManager.showInNewWindow(SceneType.PERSONAL_DATA);
        });
    }
}