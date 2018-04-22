package com.project.manager.controllers.admin;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableView;
import com.project.manager.models.MessageTableView;
import com.project.manager.models.ProjectTableView;
import com.project.manager.models.UserTableView;
import com.project.manager.ui.components.admin.AdminDashboardTablesComponent;
import com.project.manager.ui.sceneManager.SceneManager;
import com.project.manager.ui.sceneManager.SceneType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TreeItem;
import lombok.Getter;
import lombok.Setter;
import org.apache.log4j.Logger;
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
    private SceneManager sceneManager;
    private final Logger logger;

    /**
     * The constructor of this Spring Bean contains reference to {@link AdminDashboardTablesComponent},
     * this class are injected to this controller to manage components of AdminDashboard Window
     * Inside constructor is created reference to SessionManager {@link SceneManager} to switch some other
     * windows
     *
     * @param adminDashboardTablesComponent
     */
    @Autowired
    public AdminDashboardController(AdminDashboardTablesComponent adminDashboardTablesComponent) {
        this.adminDashboardTablesComponent = adminDashboardTablesComponent;
        this.sceneManager = SceneManager.getInstance();
        this.logger = Logger.getLogger(AdminDashboardController.class);
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

    /**
     * This method is responsible for listening the controller in window, and making action
     * implemented in lambdas expression
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        adminDashboardTablesComponent.generateProjectTableView();

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

        updateProject.setOnAction(e -> sceneManager.showInNewWindow(SceneType.ADMIN_UPDATE_PROJECT));
    }
}
