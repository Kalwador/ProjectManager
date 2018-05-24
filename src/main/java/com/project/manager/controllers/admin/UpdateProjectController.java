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
 * This is the class which is responsible to manage the update project window,
 * that class include all necessary references to JavaFX components which will be use to send value to update the
 * selected project in future
 */
@Getter
@Setter
@Component
public class UpdateProjectController implements Initializable {

    @FXML
    private JFXButton accept;

    @FXML
    private JFXButton cancel;

    @FXML
    private Label managerName;

    @FXML
    private Label managerErrorLabel;

    @FXML
    private Label memberErrorLabel;

    @FXML
    private JFXButton changeManager;

    @FXML
    private JFXButton addMember;
    @FXML

    private VBox projectMembersArea;

    @FXML
    private TextField insertProjectInfo;

    @FXML
    private TextField insertProjectName;

    @FXML
    private TextField newManagerTextField;

    @FXML
    private TextField newMemberTextField;

    @FXML
    private ImageView managerAvatar;

    private ProjectService projectService;
    private SessionService sessionService;
    private SceneManager sceneManager;
    private MemberPaneGenerator memberPaneGenerator;
    private UserSelectorService userSelectorService;

    private UserModel newManager;

    private Image convertedManagerAvatar;

    private List<String> possibleUsers;
    private AutoCompletionBinding<String> autoCompletionBinding;
    private AdminDashboardTablesComponent adminDashboardTablesComponent;
    private AdminDashboardController adminDashboardController;
    private InjectAvatar injectAvatar;

    @Autowired
    public UpdateProjectController(MemberPaneGenerator memberPaneGenerator,
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
        resetErrorLabel(managerErrorLabel);
        resetErrorLabel(memberErrorLabel);

        Project currentProject = sessionService.getProject();

        possibleUsers = userSelectorService.getOnlyUserList();
        TextFields.bindAutoCompletion(newManagerTextField, possibleUsers);
        TextFields.bindAutoCompletion(newMemberTextField, possibleUsers);

        byte[] userAvatar = currentProject.getManager().getAvatar();

        if (Optional.ofNullable(userAvatar).isPresent()) {
            convertedManagerAvatar = ImageConverter.convertToImage(userAvatar, 100, 100);
        }

        managerAvatar.setImage(convertedManagerAvatar);

        String fullName = currentProject.getManager().getFirstName() + " "
                + currentProject.getManager().getLastName();

        insertProjectName.setText(currentProject.getProjectName());
        insertProjectInfo.setText(currentProject.getProjectInformation());
        managerName.setText(fullName);

        memberPaneGenerator.createPanes(projectMembersArea);
        changeManager.setOnAction(event -> {
            changeUserAsManager();
        });

        addMember.setOnAction(event -> {
            addUserAsMember();
        });


        /**
         * Accept changes in project action listener
         * Performs methods responsible for update project name, update project description,
         * update project manager and update project members in update project view.
         */
        accept.setOnAction(event -> {
            Alert alert = AlertManager.showConfirmationAlert("Confirm changes", "Do you want to accept these changes?");
            if (alert.getResult().equals(ButtonType.OK)) {
                Project project = sessionService.getProject();
                projectService.updateName(project, insertProjectName.getText());
                projectService.updateDescription(project, insertProjectInfo.getText());
                projectService.updateManager(project, newManager);
                projectService.updateMembers(project);
                adminDashboardTablesComponent.generateProjectTableView();
                memberPaneGenerator.getMembers().clear();
                sessionService.setProject(null);
                sceneManager.closeNewWindow(SceneType.ADMIN_UPDATE_PROJECT);
                adminDashboardController.getDeleteUsers().setDisable(true);
                adminDashboardController.getDeleteProject().setDisable(true);
                adminDashboardController.getShowProject().setDisable(true);
                adminDashboardController.getUpdateProject().setDisable(true);
            }
        });

        /**
         * Cancel changes in project action listener
         * Reject all changes in selected project in update project view.
         */
        cancel.setOnAction(event -> {
            Alert alert = AlertManager.showConfirmationAlert("Reject changes", "Do you want to reject these changes?");
            if (alert.getResult().equals(ButtonType.OK)) {
                sceneManager.closeNewWindow(SceneType.ADMIN_UPDATE_PROJECT);
                adminDashboardTablesComponent.generateProjectTableView();
                memberPaneGenerator.getMembers().clear();
                sessionService.setProject(null);
                adminDashboardController.getDeleteUsers().setDisable(true);
                adminDashboardController.getDeleteProject().setDisable(true);
                adminDashboardController.getShowProject().setDisable(true);
                adminDashboardController.getUpdateProject().setDisable(true);
            }
        });
    }

    /**
     * This method is responsible for changing the manager of current selected project
     * in update project view.
     */
    public void changeUserAsManager() {
        resetErrorLabel(managerErrorLabel);
        try {
            String manager = newManagerTextField.getText();
            newManager = userSelectorService.findUser(manager);
            String fullName = newManager.getFirstName() + " " + newManager.getLastName();
            managerName.setText(fullName);
        } catch (UserDoesNotExistException e) {
            setErrorLabel(managerErrorLabel, "User " + newManagerTextField.getText() + " does not exist!");
        } catch (NotEnoughPermissionsException e) {
            setErrorLabel(managerErrorLabel, "You don't have enough permission to change manager");
        } catch (EmptyUsernameException e) {
            setErrorLabel(managerErrorLabel, "Please insert a name of user to change manager");
        }
    }

    /**
     * This method is responsible for adding new member into current selected project
     * in update project view.
     */
    public void addUserAsMember() {
        resetErrorLabel(memberErrorLabel);
        try {
            String username = newMemberTextField.getText();
            UserModel possibleMember = userSelectorService.findUser(username);
            List<UserModel> members = memberPaneGenerator.getMembers();
            if (members.stream().anyMatch(member -> member.getId().equals(possibleMember.getId()))) {
                setErrorLabel(memberErrorLabel, "This user exist as member");
            } else {
                memberPaneGenerator.getMembers().add(possibleMember);
                projectMembersArea.getChildren().clear();
                memberPaneGenerator.createTempPanes(projectMembersArea);
            }
        } catch (UserDoesNotExistException e) {
            setErrorLabel(memberErrorLabel, "User " + newMemberTextField.getText() + " does not exist!");
        } catch (NotEnoughPermissionsException e) {
            setErrorLabel(memberErrorLabel, "You don't have enough permission to change manager");
        } catch (EmptyUsernameException e) {
            setErrorLabel(memberErrorLabel, "Please insert a name of user to change manager");
        }
    }

    /**
     * This method is responsible for deleting actual member from current selected project
     * in update project view.
     */
    public void deleteFromMembers(UserModel memberToDelete) {
        List<UserModel> members = memberPaneGenerator.getMembers();
        members.removeIf(userModel -> userModel.getId().equals(memberToDelete.getId()));
        projectMembersArea.getChildren().clear();
        memberPaneGenerator.createTempPanes(projectMembersArea);
    }

    /**
     * This method is responsible for resetting error labels for specified roles in update project view.
     *
     * @param label to hide
     */
    private void resetErrorLabel(Label label) {
        label.setText("");
        label.setVisible(false);
    }

    /**
     * This method is responsible for setting error labels for specified roles in update project view.
     *
     * @param label   label to show and specify message
     * @param message message to write in label
     */
    private void setErrorLabel(Label label, String message) {
        label.setVisible(true);
        label.setText(message);

    }
}
