package com.project.manager.ui.components;

import com.project.manager.controllers.admin.MemberPaneController;
import com.project.manager.data.InjectAvatar;
import com.project.manager.entities.UserModel;
import com.project.manager.services.SessionService;
import com.project.manager.utils.ImageConverter;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@Component
public class MemberPaneGenerator {

    private Image convertedMemberAvatar = null;

    private InjectAvatar injectAvatar;
    private SessionService sessionService;
    private List<UserModel> members;

    @Autowired
    public MemberPaneGenerator(InjectAvatar injectAvatar) {
        this.sessionService = SessionService.getInstance();
        this.injectAvatar = injectAvatar;
    }

    /**
     * This method perform generating of members panes of selected project witch will be displayed
     * into updateProjectView in Admin Dashboard
     *
     * @param projectMembersArea - VBox that contains panes with members
     */
    public void createPanes(VBox projectMembersArea) {
        members = new ArrayList<>();
        members.addAll(sessionService.getProject().getMembers());
        members.forEach(project -> {
            createProjectBrick(projectMembersArea, project);
        });
    }

    /**
     * This method perform regenerating of members panes after changes in project members in updateProjectView
     * of selected project which will be displayed in Admin Dashboard
     *
     * @param projectMembersArea - VBox that contains panes with members
     */
    public void createTempPanes(VBox projectMembersArea) {
        members.forEach(project -> {
            createProjectBrick(projectMembersArea, project);
        });
    }

    /**
     * This method perform logic of generating members panes of selected project witch will be displayed into
     * projectTableView in Admin Dashboard
     *
     * @param projectMembersArea - VBox that contains panes of members
     * @param userModel      - model of user
     */
    private void createProjectBrick(VBox projectMembersArea, UserModel userModel) {
        try {
            if (Optional.ofNullable(userModel.getAvatar()).isPresent()){
                convertedMemberAvatar = ImageConverter.convertToImage(userModel.getAvatar(), 100, 100);
            }
            String fullName = userModel.getFirstName() + " " + userModel.getLastName();
            AnchorPane newAnchorPane;
            MemberPaneController controller = new MemberPaneController();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/admin/memberPane.fxml"));
            fxmlLoader.setController(controller);
            newAnchorPane = fxmlLoader.load();
            controller.setMemberId(userModel.getId());
            controller.getMemberName().setText(fullName);
            if (Optional.ofNullable(convertedMemberAvatar).isPresent())
                controller.getMemberAvatar().setImage(convertedMemberAvatar);

            projectMembersArea.getChildren().add(newAnchorPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
