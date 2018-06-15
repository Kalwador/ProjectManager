package com.project.manager.controllers.admin;

import com.jfoenix.controls.JFXButton;
import com.project.manager.config.ApplicationContextProvider;
import com.project.manager.entities.Project;
import com.project.manager.entities.UserModel;
import com.project.manager.services.SessionService;
import com.project.manager.services.user.UserService;
import com.project.manager.ui.sceneManager.SceneManager;
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
 * This is the class which is responsible for each pane view with project member.
 * This class perform actions on members which are on selected project.
 */

@Getter
@Setter
public class MemberPaneController implements Initializable {

    @FXML
    private JFXButton deleteMember;

    @FXML
    private Label memberName;

    @FXML
    private ImageView memberAvatar;

    private Long memberId;
    private SessionService session;
    private SceneManager sceneManager;
    private UserService userService;
    private UpdateProjectController updateProjectController;
    private NewProjectController newProjectController;

    public MemberPaneController() {
        userService = ApplicationContextProvider.getInstance().getContext().getBean(UserService.class);
        updateProjectController = ApplicationContextProvider.getInstance().getContext().getBean(UpdateProjectController.class);
        newProjectController = ApplicationContextProvider.getInstance().getContext().getBean(NewProjectController.class);
        sceneManager = SceneManager.getInstance();
    }

    /**
     * Initialization of actions on project members like add or delete member
     *
     * @param location  URL location
     * @param resources Bundle resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        session = SessionService.getInstance();
        deleteMember.setOnAction(e -> {
            Optional<UserModel> member = userService.getUserById(memberId);
            Optional<Project> project = Optional.ofNullable(session.getProject());
            if (project.isPresent())
                member.ifPresent(userModel -> updateProjectController.deleteFromMembers(userModel));
            else
                member.ifPresent(userModel -> newProjectController.deleteFromMembersOfNewProject(userModel));
        });
    }
}
