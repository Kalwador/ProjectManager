package com.project.manager.models;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.project.manager.entities.UserModel;
import com.project.manager.ui.GraphicButtonGenerator;
import javafx.beans.property.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserTableView extends RecursiveTreeObject<UserTableView> {

    private LongProperty id;

    private StringProperty username;

    private StringProperty email;

    private StringProperty role;

    private IntegerProperty countOfProjects;

    private BooleanProperty isLocked;

    private SimpleObjectProperty<JFXButton> lockOrUnlock;

    private SimpleObjectProperty<JFXButton> resetPass;

    private SimpleObjectProperty<JFXButton> delete;

    private SimpleObjectProperty<JFXCheckBox> check;

    /**
     * This is the method which convert original UserModel entity from database to our class for making pretty view class
     * to displaying information about user for admin
     *
     * @param userModel This parameter is original userModel for converting to UserModel view class
     * @return method return already converted original userModel to userModel view class
     */
    public static UserTableView convert(UserModel userModel) {
        return UserTableView.builder()
                .id(new SimpleLongProperty(userModel.getId()))
                .username(new SimpleStringProperty(userModel.getUsername()))
                .email(new SimpleStringProperty(userModel.getEmail()))
                .role(new SimpleStringProperty(userModel.getRole().toString()))
                .countOfProjects(new SimpleIntegerProperty(userModel.getProjectsAsManager().size() +
                        userModel.getProjectsAsUser().size()))
                .isLocked(new SimpleBooleanProperty(userModel.isLocked()))
                .check(new SimpleObjectProperty<>(new JFXCheckBox()))
                .build();
    }

    /**
     * This is the method to generate delete button in table
     *
     * @param userTableView this is the userView object for modify it to contain this button inside
     * @return method will return UserView object with delete button inside
     */
    public UserTableView generateDelButton(UserTableView userTableView) {
        JFXButton del = new GraphicButtonGenerator().getJfxButtonWithGraphic("/images/delete.png");
        userTableView.setDelete(new SimpleObjectProperty(del));
        return userTableView;
    }

    /**
     * This is the method to generate reset button in table
     *
     * @param userTableView this is the userView object for modify it to contain this button inside
     * @return method will return UserView object with reset button inside
     */
    public UserTableView generateResetButton(UserTableView userTableView) {
        JFXButton reset = new JFXButton("Reset PASSWORD");
        reset.setButtonType(JFXButton.ButtonType.RAISED);
        reset.setStyle("-fx-background-color: #be2e22");
        userTableView.setResetPass(new SimpleObjectProperty(reset));
        return userTableView;
    }

    /**
     * This is the method to generate unlock button in table
     *
     * @param userTableView this is the userView object for modify it to contain this button inside
     * @return method will return UserView object with unlock button inside
     */
    public UserTableView generateLockOrUnlockButton(UserTableView userTableView) {
        JFXButton lock = null;
        GraphicButtonGenerator gbg = new GraphicButtonGenerator();
        if (userTableView.getIsLocked().get()) {
            lock = gbg.getJfxButtonWithGraphic("/images/lock.png");
        } else {
            lock = gbg.getJfxButtonWithGraphic("/images/unlock.png");
        }
        userTableView.setLockOrUnlock(new SimpleObjectProperty<>(lock));
        return userTableView;
    }
}
