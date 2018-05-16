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
     * @param location default framework parameter
     * @param resources default framework parameter
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
         * If user exists, compares passed PASSWORD with PASSWORD in database
         * When passwords match, login is achieved
         */
        loginButton.setOnAction(e -> {
            this.resetUsernameError();
            this.resetPasswordError();
            try {
                String username = usernameTextField.getText();
                String passedPassword = passwordPassField.getText();
                loginService.loginUser(username, passedPassword, rememberUser.isSelected());
            }
            catch (DifferentPasswordException dpe) {
                labelErrorPassword.setVisible(true);
                labelErrorPassword.setText(dpe.getMessage());
            }
            catch (UserDoesNotExistException | EmptyUsernameException udnee) {
                labelErrorUsername.setVisible(true);
                labelErrorUsername.setText(udnee.getMessage());
            } catch (EmptyPasswordException epe){
                labelErrorUsername.setVisible(true);
                labelErrorPassword.setText(epe.getMessage());
            }
            catch (AccountLockedException | AccountBlockedException ex) {
                AlertManager.showInformationAlert("Account blocked!", ex.getMessage());
            }
        });
        forgotPasswordButton.setOnAction(e -> sceneManager.showScene(SceneType.RESET_PASSWORD));
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
