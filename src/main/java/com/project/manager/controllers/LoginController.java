package com.project.manager.controllers;

import com.project.manager.exceptions.DifferentPasswordException;
import com.project.manager.exceptions.EmptyPasswordException;
import com.project.manager.exceptions.EmptyUsernameException;
import com.project.manager.exceptions.UserDoesNotExistException;
import com.project.manager.ui.sceneManager.SceneManager;
import com.project.manager.ui.sceneManager.SceneType;
import com.project.manager.services.LoginService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

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

    private SceneManager sceneManager;
    private LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        sceneManager = SceneManager.getInstance();
        this.loginService=loginService;
    }

    /**
     * Initialization of login frame
     * @param location //TODO
     * @param resources //TODO
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.resetUsernameError();
        this.resetPasswordError();

        /** -----------------------------todo
         * Register action listener
         * Closes login window and opens menu to register new account
         * Registration window - in progress by Seba
         */

        register.setOnAction(e -> {
            sceneManager.showScene(SceneType.REGISTRATION);
        });

        /**
         * Login action listener
         * Searches passed username in database and checks if it exists
         * If user exists, compares passed password with password in database
         * When passwords match, login is achieved
         */
        loginButton.setOnAction(e -> {
            this.resetUsernameError();
            this.resetPasswordError();
            try {
                String username = usernameTextField.getText();
                String passedPassword = passwordPassField.getText();
                loginService.loginUser(username, passedPassword);
            }
            catch (DifferentPasswordException dpe) {
                labelErrorPassword.setText(dpe.getMessage());
            }
            catch (UserDoesNotExistException | EmptyUsernameException udnee) {
                labelErrorUsername.setVisible(true);
                labelErrorUsername.setText(udnee.getMessage());
            } catch (EmptyPasswordException epe){
                labelErrorUsername.setVisible(true);
                labelErrorPassword.setText(epe.getMessage());
            }
        });
        forgotPasswordButton.setOnAction(e -> sceneManager.showScene(SceneType.RESETPASSWD));
    }

    private void resetUsernameError() {
        labelErrorUsername.setText("");
        labelErrorUsername.setVisible(false);
    }

    private void resetPasswordError() {
        labelErrorPassword.setText("");
        labelErrorPassword.setVisible(false);
    }
}
