package com.project.manager.controllers;

import com.project.manager.services.UserSelectorService;
import com.project.manager.ui.components.ProjectPaneGenerator;
import com.project.manager.ui.sceneManager.SceneManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
@Component
public class UserSelectorController implements Initializable {

    private SceneManager sceneManager;

    private ProjectPaneGenerator projectPaneGenerator;
    private UserSelectorService userSelectorService;

    List<String> possibleUsers;
    private AutoCompletionBinding<String> autoCompletionBinding;

    @FXML
    private Label errorLabel;
    @FXML
    private Button addUserButton;
    @FXML
    private TextField usernameTextField;

    @Autowired
    public UserSelectorController(UserSelectorService userSelectorService) {
        sceneManager = SceneManager.getInstance();
        this.userSelectorService = userSelectorService;
    }

    /**
     * Initialization of AddUser view with list of users
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.reserLabelError();
        possibleUsers = userSelectorService.getUserList();
        TextFields.bindAutoCompletion(usernameTextField, possibleUsers);

        addUserButton.setOnMouseClicked(e -> {
            this.reserLabelError();
            try {
                String username = usernameTextField.getText().toString();
                userSelectorService.findUser(username);
            } catch (RuntimeException ex) {
                errorLabel.setVisible(true);
                errorLabel.setText(ex.getMessage());
            }
        });
    }

    /**
     * Method responsible for resetting error label.
     */
    public void reserLabelError() {
        errorLabel.setText("");
        errorLabel.setVisible(false);
    }
}
