package com.project.manager.controllers;

import com.jfoenix.controls.JFXButton;
import com.project.manager.sceneManager.SceneManager;
import com.project.manager.sceneManager.SceneType;
import com.project.manager.services.ResetPasswdService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.net.URL;
import java.util.ResourceBundle;

@Component
public class ResetPasswdController implements Initializable {


    @FXML
    private Button backToLoginButton;//button for testing

    @FXML
    private TextField usernameOrEmailField;

    @FXML
    private Label usernameOrEmailErrorLabel;

    @FXML
    private JFXButton resetPasswdButton;

    private SceneManager sceneManager;
    private ResetPasswdService resetPasswdService;

    @Autowired
    public ResetPasswdController(ResetPasswdService resetPasswdService) {
        sceneManager = SceneManager.getInstance();
        this.resetPasswdService = resetPasswdService;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        resetUsernameOrEmailError();

        backToLoginButton.setOnAction(e -> {
            sceneManager.showScene(SceneType.LOGIN);
        });

        resetPasswdButton.setOnAction(e -> {
            resetUsernameOrEmailError();

            try {
                String usernameOrEmail = usernameOrEmailField.getText().toString();
                resetPasswdService.resetPassword(usernameOrEmail);
                //sceneManager.showScene(SceneType.DASHBOARD);
            } catch (RuntimeException ex) {
                usernameOrEmailErrorLabel.setVisible(true);
                usernameOrEmailErrorLabel.setText(ex.getMessage());
            }

//            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//            alert.setTitle("Confirmation Dialog");
//            alert.setHeaderText("Look, a Confirmation Dialog");
//            alert.setContentText("Are you ok with this?");
//
//            alert.showAndWait();

        });

    }

    public void resetUsernameOrEmailError() {
        usernameOrEmailErrorLabel.setText("");
        usernameOrEmailErrorLabel.setVisible(false);
    }
}
