package com.project.manager.controllers;

import com.project.manager.exceptions.EmptyUsernameException;
import com.project.manager.exceptions.NotEnoughPermissionsException;
import com.project.manager.exceptions.user.UserDoesNotExistException;
import com.project.manager.services.user.UserSelectorService;
import com.project.manager.ui.components.ProjectPaneGenerator;
import com.project.manager.ui.sceneManager.SceneManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import lombok.extern.log4j.Log4j;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * This is the class which is responsible for adding users window.
 * This class perform display autobinding text field and button.
 */
@Log4j
@Component
public class UserSelectorController implements Initializable {

    @FXML
    private Label errorLabel;
    @FXML
    private Button addUserButton;
    @FXML
    private TextField usernameTextField;

    private SceneManager sceneManager;
    private ProjectPaneGenerator projectPaneGenerator;
    private UserSelectorService userSelectorService;


    private List<String> possibleUsers;
    private AutoCompletionBinding<String> autoCompletionBinding;

    @Autowired
    public UserSelectorController(UserSelectorService userSelectorService) {
        this.sceneManager = SceneManager.getInstance();
        this.userSelectorService = userSelectorService;
    }

    /**
     * Initialization of AddUser view with list of users
     *
     * @param location URL location
     * @param resources Bundle resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resetErrorLabel();
        loadUsers();
        TextFields.bindAutoCompletion(usernameTextField, possibleUsers);

        addUserButton.setOnMouseClicked(e -> {
            this.resetErrorLabel();
            try {
                String username = usernameTextField.getText();
                userSelectorService.findUser(username);
            } catch (EmptyUsernameException ex) {
                setErrorLabelMessage("User not recognized, username is empty!");
                log.error("User try to insert use with empty username : " + usernameTextField.getText());
            } catch (UserDoesNotExistException ex) {
                setErrorLabelMessage("User with username : '" + usernameTextField.getText() + "' does not exist!");
                log.warn("User with username : '" + usernameTextField.getText() + "' was not found!");
            } catch (NotEnoughPermissionsException ex) {
                setErrorLabelMessage("You don't have enough permissions to execute that action!");
                log.warn("User try to execute prohibited for him actions!");
            }
        });
    }

    private void setErrorLabelMessage(String message) {
        errorLabel.setVisible(true);
        errorLabel.setText(message);
    }

    /**
     * Method responsible for resetting error label.
     */
    public void resetErrorLabel() {
        errorLabel.setText("");
        errorLabel.setVisible(false);
    }

    private void loadUsers() {
        try {
            possibleUsers = userSelectorService.getUserList();
        } catch (UserDoesNotExistException e) {
            log.warn("Load users error, already logged account try to load user which does not exist in database!");
            setErrorLabelMessage("User(s) does not exist in database!");
        }
    }
}
