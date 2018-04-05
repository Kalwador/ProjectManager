package com.project.manager.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.project.manager.sceneManager.SceneManager;
import com.project.manager.sceneManager.SceneType;
import com.project.manager.services.ResetPasswdService;
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
public class ResetPasswdController implements Initializable {

    @FXML
    private Button backToLoginButton;//button for testing

    @FXML
    private Button backToLoginButton1;//button for testing

    @FXML
    private Button backToLoginButton2;//button for testing

    @FXML
    private JFXTextField usernameOrEmailField;

    @FXML
    private Label usernameOrEmailErrorLabel;

    @FXML
    private JFXButton resetPasswdButton;

    @FXML
    private Pane resetPasswdStepOne;

    @FXML
    private Pane resetPasswdStepTwo;

    @FXML
    private Pane resetPasswdStepThree;

    @FXML
    private JFXTextField generatedCodeField;

    @FXML
    private Label codeErrorLabel;

    @FXML
    private JFXButton confirmButton;

    @FXML
    private JFXPasswordField passwdField;

    @FXML
    private JFXPasswordField repeatPasswdField;

    @FXML
    private Label passwdErrorLabel;

    @FXML
    private JFXButton changePasswdButton;

    private SceneManager sceneManager;
    private ResetPasswdService resetPasswdService;
    private String usernameOrEmail;

    @Autowired
    public ResetPasswdController(ResetPasswdService resetPasswdService) {
        this.sceneManager = SceneManager.getInstance();
        this.resetPasswdService = resetPasswdService;
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

        resetPasswdStepOne.setVisible(true);
        resetPasswdStepTwo.setVisible(false);
        resetPasswdStepThree.setVisible(false);

        resetUsernameOrEmailError();
        resetCodeErrorLabel();
        resetPasswdLabel();

        backToLoginButton.setOnAction(e -> sceneManager.showScene(SceneType.LOGIN));

        backToLoginButton1.setOnAction(e -> sceneManager.showScene(SceneType.LOGIN));

        backToLoginButton2.setOnAction(e -> sceneManager.showScene(SceneType.LOGIN));

        /**
         * Reset password action listener
         * perform first step of reset password procedure that typing user name or email
         */
        resetPasswdButton.setOnAction(e -> {
            resetUsernameOrEmailError();

            try {
                usernameOrEmail = usernameOrEmailField.getText();
                resetPasswdService.resetPassword(usernameOrEmail);
                resetPasswdStepOne.setVisible(false);
                resetPasswdStepTwo.setVisible(true);
            } catch (RuntimeException ex) {
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
                resetPasswdService.checkGeneratedCode(usernameOrEmail, generatedCode);
                resetPasswdStepTwo.setVisible(false);
                resetPasswdStepThree.setVisible(true);
            } catch (RuntimeException ex) {
                codeErrorLabel.setVisible(true);
                codeErrorLabel.setText(ex.getMessage());
            }
        });

        changePasswdButton.disableProperty().bind(Bindings.isEmpty(passwdField.textProperty())
                .or(Bindings.length(passwdField.textProperty()).lessThan(8))
                .or(Bindings.length(repeatPasswdField.textProperty()).lessThan(8)));

        /**
         * Change password action listener
         * perform third step of reset password procedure that typed and confirmed password is going to change.
         */
        changePasswdButton.setOnAction(e -> {
            resetPasswdLabel();

            try {
                usernameOrEmail = usernameOrEmailField.getText();
                String password = passwdField.getText();
                String repeatPassword = repeatPasswdField.getText();
                resetPasswdService.changePassword(usernameOrEmail, password, repeatPassword);
            } catch (RuntimeException ex) {
                passwdErrorLabel.setVisible(true);
                passwdErrorLabel.setText(ex.getMessage());
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
    public void resetPasswdLabel() {
        passwdErrorLabel.setText("");
        passwdErrorLabel.setVisible(false);
    }
}
