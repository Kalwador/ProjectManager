package com.project.manager.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.project.manager.exceptions.DifferentGeneratedCodeException;
import com.project.manager.exceptions.EmptyGeneratedCodeException;
import com.project.manager.services.SessionService;
import com.project.manager.services.login.FirstLoginService;
import com.project.manager.ui.AlertManager;
import com.project.manager.ui.sceneManager.SceneManager;
import com.project.manager.ui.sceneManager.SceneType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class FirstLoginController implements Initializable {
    @FXML
    private JFXTextField generatedCodeField;
    @FXML
    private JFXButton backToLoginButton;
    @FXML
    private JFXButton confirmButton;
    @FXML
    private Label codeErrorLabel;

    private SceneManager sceneManager;
    private FirstLoginService firstLoginService;
    private SessionService sessionService;

    @Autowired
    public FirstLoginController(FirstLoginService firstLoginService) {
        this.sceneManager = SceneManager.getInstance();
        this.firstLoginService = firstLoginService;
        this.sessionService = SessionService.getInstance();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        resetCodeErrorLabel();
        backToLoginButton.setOnAction(e -> {
            sessionService.setUserModel(null);
            sceneManager.showScene(SceneType.LOGIN);
        });

        confirmButton.setOnAction(e -> {
            resetCodeErrorLabel();
            try {
                String generatedCode = generatedCodeField.getText();
                firstLoginService.checkGeneratedCode(generatedCode);
                AlertManager.showInformationAlert("Account activated", "Your account now is activated! Please login again.");
            } catch (DifferentGeneratedCodeException ex) {
                codeErrorLabel.setVisible(true);
                codeErrorLabel.setText("Inserted code is different than generated code!");
            } catch (EmptyGeneratedCodeException e1) {
                codeErrorLabel.setVisible(true);
                codeErrorLabel.setText("Inserted code is empty, please insert valid code!");
            }
        });
    }

    private void resetCodeErrorLabel() {
        codeErrorLabel.setText("");
        codeErrorLabel.setVisible(false);
    }
}
