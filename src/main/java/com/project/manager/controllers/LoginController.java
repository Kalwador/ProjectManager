package com.project.manager.controllers;

import com.project.manager.exceptions.*;
import com.project.manager.exceptions.user.UserDoesNotExistException;
import com.project.manager.services.login.LoginService;
import com.project.manager.ui.AlertManager;
import com.project.manager.ui.sceneManager.SceneManager;
import com.project.manager.ui.sceneManager.SceneType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Log4j
@Component
public class LoginController implements Initializable {

    @FXML
    private Button register;
    @FXML
    private Button loginButton;
    @FXML
    private Button forgotPasswordButton;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordPassField;
    @FXML
    private Label labelErrorUsername;
    @FXML
    private Label labelErrorPassword;
    @FXML
    private CheckBox rememberUser;

    private SceneManager sceneManager;
    private LoginService loginService;


    @Autowired
    public LoginController(LoginService loginService) {
        this.sceneManager = SceneManager.getInstance();
        this.loginService = loginService;
    }

    /**
     * Initialization of login frame
     *
     * @param location  default framework properties
     * @param resources default framework properties
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resetUsernameError();
        this.resetPasswordError();

        /**
         * Register action listener
         * Closes login window and opens menu to register new account
         * Registration window
         */

        register.setOnAction(e -> {
            sceneManager.showScene(SceneType.REGISTRATION);
        });

        /**
         * Login action listener
         * Searches passed username in database and checks if it exists
         * If user exists, compares passed PASSWORD with PASSWORD in database
         * When passwords match, login is achieved
         */
        loginButton.setOnAction(e -> logInUser());
        forgotPasswordButton.setOnAction(e -> sceneManager.showScene(SceneType.RESET_PASSWORD));
    }

    private void logInUser() {
        this.resetUsernameError();
        this.resetPasswordError();
        try {
            String username = usernameTextField.getText();
            String passedPassword = passwordPassField.getText();
            loginService.loginUser(username, passedPassword, rememberUser.isSelected());
        } catch (WrongPasswordException ex) {
            labelErrorPassword.setVisible(true);
            labelErrorPassword.setText("Wrong password!");
        } catch (EmptyUsernameException ex) {
            labelErrorUsername.setVisible(true);
            labelErrorUsername.setText("Username field is empty, please insert your username!");
        } catch (EmptyPasswordException ex) {
            labelErrorPassword.setVisible(true);
            labelErrorPassword.setText("Password field is empty, please insert password!");
        } catch (AccountLockedException ex) {
            AlertManager.showInformationAlert("Account locked!", "Your account is locked, " +
                    "you should contact with administrator to unlock your account");
        } catch (AccountBlockedException ex) {
            AlertManager.showInformationAlert("Account blocked!", "Your account is blocked, " +
                    "you can unblock it by resetting a password in login screen -> forget password");
        } catch (UserDoesNotExistException ex) {
            log.warn("The user with username : '" + usernameTextField.getText() + "' was not found!");
            setErrorLabelMessage(labelErrorUsername, "The user with that username was not found!");
        }
    }

    private void resetUsernameError() {
        labelErrorUsername.setText("");
        labelErrorUsername.setVisible(false);
    }

    private void resetPasswordError() {
        labelErrorPassword.setText("");
        labelErrorPassword.setVisible(false);
    }

    private void setErrorLabelMessage(Label label, String message) {
        label.setVisible(true);
        label.setText(message);
    }
}
