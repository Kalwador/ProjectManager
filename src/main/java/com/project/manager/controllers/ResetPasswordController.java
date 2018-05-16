package com.project.manager.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.project.manager.exceptions.EmailValidationException;
import com.project.manager.services.ResetPasswordService;
import com.project.manager.ui.sceneManager.SceneManager;
import com.project.manager.ui.sceneManager.SceneType;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class ResetPasswordController implements Initializable {
    @FXML
    private Button backToLoginButton;
    @FXML
    private Button backToLoginButton1;
    @FXML
    private Button backToLoginButton2;
    @FXML
    private JFXTextField usernameOrEmailField;
    @FXML
    private Label usernameOrEmailErrorLabel;

    @FXML
    private JFXButton resetPasswordButton;

    @FXML
    private Pane resetPasswordStepOne;

    @FXML
    private Pane resetPasswordStepTwo;

    @FXML
    private Pane resetPasswordStepThree;

    @FXML
    private JFXTextField generatedCodeField;

    @FXML
    private Label codeErrorLabel;

    @FXML
    private JFXButton confirmButton;

    @FXML
    private JFXPasswordField passwordField;

    @FXML
    private JFXPasswordField repeatPasswordField;

    @FXML
    private Label passwordErrorLabel;

    @FXML
    private JFXButton changePasswordButton;

    private SceneManager sceneManager;
    private ResetPasswordService resetPasswordService;
    private String usernameOrEmail;


    @Autowired
    public ResetPasswordController(ResetPasswordService resetPasswordService) {
        this.sceneManager = SceneManager.getInstance();
        this.resetPasswordService = resetPasswordService;
    }

    /**
     * This method is responsible for listening the controller in window, and making action after button clicked
     * implemented in lambdas expression
     *
     * @param location  - URL location
     * @param resources - Bundle resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        resetPasswordStepOne.setVisible(true);
        resetPasswordStepTwo.setVisible(false);
        resetPasswordStepThree.setVisible(false);

        resetUsernameOrEmailError();
        resetCodeErrorLabel();
        resetPasswordLabel();

        backToLoginButton.setOnAction(e -> sceneManager.showScene(SceneType.LOGIN));

        backToLoginButton1.setOnAction(e -> sceneManager.showScene(SceneType.LOGIN));

        backToLoginButton2.setOnAction(e -> sceneManager.showScene(SceneType.LOGIN));

        /**
         * Reset password action listener
         * perform first step of reset password procedure that typing user name or email
         */
        resetPasswordButton.setOnAction(e -> {
            resetUsernameOrEmailError();

            try {
                usernameOrEmail = usernameOrEmailField.getText();
                resetPasswordService.resetPassword(usernameOrEmail);
                resetPasswordStepOne.setVisible(false);
                resetPasswordStepTwo.setVisible(true);
            } catch (EmailValidationException ex) {
                usernameOrEmailErrorLabel.setVisible(true);
                usernameOrEmailErrorLabel.setText(ex.getMessage());
            }
        });

        /**
         * Confirm generated code action listener
         * perform second step of reset password procedure that checking if typed generated code are the same like in database
         */
        confirmButton.setOnAction(e -> {
            resetCodeErrorLabel();

            try {
                usernameOrEmail = usernameOrEmailField.getText();
                String generatedCode = generatedCodeField.getText();
                resetPasswordService.checkGeneratedCode(usernameOrEmail, generatedCode);
                resetPasswordStepTwo.setVisible(false);
                resetPasswordStepThree.setVisible(true);
            } catch (RuntimeException ex) {
                codeErrorLabel.setVisible(true);
                codeErrorLabel.setText(ex.getMessage());
            }
        });

        changePasswordButton.disableProperty().bind(Bindings.isEmpty(passwordField.textProperty())
                .or(Bindings.length(passwordField.textProperty()).lessThan(8))
                .or(Bindings.length(repeatPasswordField.textProperty()).lessThan(8)));

        /**
         * Change password action listener
         * perform third step of reset password procedure that typed and confirmed password is going to change.
         */
        changePasswordButton.setOnAction(e -> {
            resetPasswordLabel();

            try {
                usernameOrEmail = usernameOrEmailField.getText();
                String password = passwordField.getText();
                String repeatPassword = repeatPasswordField.getText();
                resetPasswordService.changePassword(usernameOrEmail, password, repeatPassword);
            } catch (RuntimeException ex) {
                passwordErrorLabel.setVisible(true);
                passwordErrorLabel.setText(ex.getMessage());
            }
        });
    }

    /**
     * This method perform set empty string on label and set this label hidden
     */
    public void resetUsernameOrEmailError() {
        usernameOrEmailErrorLabel.setText("");
        usernameOrEmailErrorLabel.setVisible(false);
    }

    /**
     * This method perform set empty string on label and set this label hidden
     */
    public void resetCodeErrorLabel() {
        codeErrorLabel.setText("");
        codeErrorLabel.setVisible(false);
    }

    /**
     * This method perform set empty string on label and set this label hidden
     */
    public void resetPasswordLabel() {
        passwordErrorLabel.setText("");
        passwordErrorLabel.setVisible(false);
    }
}