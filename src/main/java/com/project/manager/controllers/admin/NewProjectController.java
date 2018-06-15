package com.project.manager.controllers.admin;

import com.jfoenix.controls.JFXButton;
import com.project.manager.data.InjectAvatar;
import com.project.manager.entities.Project;
import com.project.manager.entities.UserModel;
import com.project.manager.exceptions.EmptyUsernameException;
import com.project.manager.exceptions.NotEnoughPermissionsException;
import com.project.manager.exceptions.user.UserDoesNotExistException;
import com.project.manager.services.ProjectService;
import com.project.manager.services.SessionService;
import com.project.manager.services.user.UserSelectorService;
import com.project.manager.ui.AlertManager;
import com.project.manager.ui.components.MemberPaneGenerator;
import com.project.manager.ui.components.admin.AdminDashboardTablesComponent;
import com.project.manager.ui.sceneManager.SceneManager;
import com.project.manager.ui.sceneManager.SceneType;
import com.project.manager.utils.ImageConverter;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import lombok.Getter;
import lombok.Setter;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This is the class which is responsible to manage the add project window,
 * that class include all necessary references to JavaFX components which will be use to send value to add the
 * new project in future
 */
@Getter
@Setter
@Component
public class NewProjectController implements Initializable {
    @FXML
    private JFXButton create;
    @FXML
    private JFXButton cancel;
    @FXML
    private Label newManagerName;
    @FXML
    private Label newManagerErrorLabel;
    @FXML
    private Label newMemberErrorLabel;
    @FXML
    private Label projectNameRequired;
    @FXML
    private Label projectInfoRequired;
    @FXML
    private Label managerRequired;
    @FXML
    private JFXButton addManager;
    @FXML
    private JFXButton addNewMember;
    @FXML
    private VBox newProjectMembersArea;
    @FXML
    private TextField newProjectInfo;
    @FXML
    private TextField newProjectName;
    @FXML
    private TextField newManagerTextField;
    @FXML
    private TextField newMemberTextField;
    @FXML
    private ImageView newManagerAvatar;

    private UserModel addNewManager;
    private Image convertedManagerAvatar;
    private List<String> possibleUsers;
    private ProjectService projectService;
    private SessionService sessionService;
    private SceneManager sceneManager;
    private MemberPaneGenerator memberPaneGenerator;
    private UserSelectorService userSelectorService;
    private AutoCompletionBinding<String> autoCompletionBinding;
    private AdminDashboardTablesComponent adminDashboardTablesComponent;
    private AdminDashboardController adminDashboardController;
    private InjectAvatar injectAvatar;

    @Autowired
    public NewProjectController(MemberPaneGenerator memberPaneGenerator,
                                UserSelectorService userSelectorService,
                                ProjectService projectService,
                                AdminDashboardTablesComponent adminDashboardTablesComponent,
                                AdminDashboardController adminDashboardController,
                                InjectAvatar injectAvatar) {
        this.sessionService = SessionService.getInstance();
        this.projectService = projectService;
        this.sceneManager = SceneManager.getInstance();
        this.memberPaneGenerator = memberPaneGenerator;
        this.userSelectorService = userSelectorService;
        this.adminDashboardTablesComponent = adminDashboardTablesComponent;
        this.adminDashboardController = adminDashboardController;
        this.injectAvatar = injectAvatar;
    }

    /**
     * This method is responsible for listening the controller in window, and making action
     * implemented in lambdas expression
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        resetErrorLabel(newManagerErrorLabel);
        resetErrorLabel(newMemberErrorLabel);
        resetErrorLabel(projectInfoRequired, projectNameRequired, managerRequired);

        addNewManager = null;
        possibleUsers = userSelectorService.getOnlyUserList();
        TextFields.bindAutoCompletion(newManagerTextField, possibleUsers);
        TextFields.bindAutoCompletion(newMemberTextField, possibleUsers);

        addManager.setOnAction(event -> addManagerToProject());
        addNewMember.setOnAction(event -> addUserAsMemberToNewProject());

        /**
         * Accept changes in project action listener
         * Performs methods responsible for add project name, add project description,
         * add project manager and add project members in add project view.
         */
        create.setOnAction(event -> {
            resetErrorLabel(projectInfoRequired, projectNameRequired, managerRequired);
            create();
        });

        /**
         * Cancel changes in project action listener
         * Reject all changes in new project in add project view.
         */
        cancel.setOnAction(event -> cancel());
    }

    /**
     * This method is responsible for adding the manager of new project
     * in add project view.
     */
    public void addManagerToProject() {
        resetErrorLabel(newManagerErrorLabel);
        try {
            String manager = newManagerTextField.getText();
            addNewManager = userSelectorService.findUser(manager);
            String fullName = addNewManager.getFirstName() + " " + addNewManager.getLastName();
            newManagerName.setText(fullName);
            byte[] managerAvatar = addNewManager.getAvatar();
            if (Optional.ofNullable(managerAvatar).isPresent()) {
                convertedManagerAvatar = ImageConverter.convertToImage(managerAvatar, 100, 100);
            }
            newManagerAvatar.setImage(convertedManagerAvatar);
        } catch (UserDoesNotExistException e) {
            setErrorLabel(newManagerErrorLabel, "User " + newManagerTextField.getText() + " does not exist!");
        } catch (NotEnoughPermissionsException e) {
            setErrorLabel(newManagerErrorLabel, "You don't have enough permission to add manager");
        } catch (EmptyUsernameException e) {
            setErrorLabel(newManagerErrorLabel, "Please insert a name of manager");
        }
    }

    /**
     * This method is responsible for adding new member into new project
     * in add project view.
     */
    public void addUserAsMemberToNewProject() {
        resetErrorLabel(newMemberErrorLabel);
        try {
            String username = newMemberTextField.getText();
            UserModel possibleMember = userSelectorService.findUser(username);
            List<UserModel> members = memberPaneGenerator.getMembers();
            if (members.stream().anyMatch(member -> member.getId().equals(possibleMember.getId()))) {
                newMemberErrorLabel.setText("This user exist as member");
                newMemberErrorLabel.setVisible(true);
            } else {
                memberPaneGenerator.getMembers().add(possibleMember);
                newProjectMembersArea.getChildren().clear();
                memberPaneGenerator.createTempPanes(newProjectMembersArea);
            }
            if (members.isEmpty()) {
                memberPaneGenerator.getMembers().add(possibleMember);
                newProjectMembersArea.getChildren().clear();
                memberPaneGenerator.createTempPanes(newProjectMembersArea);
            }
        } catch (UserDoesNotExistException e) {
            setErrorLabel(newMemberErrorLabel, "User " + newMemberTextField.getText() + " does not exist!");
        } catch (NotEnoughPermissionsException e) {
            setErrorLabel(newMemberErrorLabel, "You don't have enough permission to add member");
        } catch (EmptyUsernameException e) {
            setErrorLabel(newMemberErrorLabel, "Please insert a name of user to add member");
        }
    }

    /**
     * This method is responsible for deleting actual member from new project
     * in add project view.
     */
    public void deleteFromMembersOfNewProject(UserModel memberToDelete) {
        List<UserModel> members = memberPaneGenerator.getMembers();
        members.removeIf(userModel -> userModel.getId().equals(memberToDelete.getId()));
        newProjectMembersArea.getChildren().clear();
        memberPaneGenerator.createTempPanes(newProjectMembersArea);
    }

    /**
     * This method is responsible for resetting error labels for project name, project description and project manager
     * in add project view.
     */
    private void resetErrorLabel(Label... labels) {
        for (Label l : labels) {
            l.setText("");
            l.setVisible(false);
        }
    }

    /**
     * This method is responsible for resetting error labels for specified roles in add project view.
     *
     * @param label to hide
     */
    private void resetErrorLabel(Label label) {
        label.setText("");
        label.setVisible(false);
    }

    /**
     * This method is responsible for setting error labels for specified roles in add project view.
     *
     * @param label   label to show and specify message
     * @param message message to write in label
     */
    private void setErrorLabel(Label label, String message) {
        label.setVisible(true);
        label.setText(message);
    }

    /**
     * This method is responsible for accept all changes in add project view.
     */
    private void create() {
        if (projectService.checkThatProjectExists(newProjectName.getText())) {
            AlertManager.showInformationAlert("Information", "This project exists in database, change the project name!");
        } else {
            if (newProjectName.getText().isEmpty()) {
                projectNameRequired.setVisible(true);
                projectNameRequired.setText("Project name cannot be empty!");
            } else if (newProjectInfo.getText().isEmpty()) {
                projectInfoRequired.setVisible(true);
                projectInfoRequired.setText("Project description cannot be empty!");
            } else if (addNewManager == null) {
                managerRequired.setVisible(true);
                managerRequired.setText("Please add a manager!");
            } else {
                Alert alert = AlertManager.showConfirmationAlert("Confirm changes", "Do you want to add this project?");
                if (alert.getResult().equals(ButtonType.OK)) {
                    Project project = new Project();
                    projectService.addNewProject(project, newProjectName.getText(), newProjectInfo.getText(), addNewManager);
                    adminDashboardTablesComponent.generateProjectTableView();
                    memberPaneGenerator.getMembers().clear();
                    sceneManager.closeNewWindow(SceneType.ADMIN_CREATE_NEW_PROJECT);
                    adminDashboardController.getDeleteUsers().setDisable(true);
                    adminDashboardController.getDeleteProject().setDisable(true);
                    adminDashboardController.getShowProject().setDisable(true);
                    adminDashboardController.getUpdateProject().setDisable(true);
                }
            }
        }
    }

    /**
     * This method is responsible for reject all changes in add project view.
     */
    private void cancel() {
        Alert alert = AlertManager.showConfirmationAlert("Reject changes", "Do you want to reject these changes?");
        if (alert.getResult().equals(ButtonType.OK)) {
            sceneManager.closeNewWindow(SceneType.ADMIN_CREATE_NEW_PROJECT);
            adminDashboardTablesComponent.generateProjectTableView();
            memberPaneGenerator.getMembers().clear();
            adminDashboardController.getDeleteUsers().setDisable(true);
            adminDashboardController.getDeleteProject().setDisable(true);
            adminDashboardController.getShowProject().setDisable(true);
            adminDashboardController.getUpdateProject().setDisable(true);
        }
    }
}
