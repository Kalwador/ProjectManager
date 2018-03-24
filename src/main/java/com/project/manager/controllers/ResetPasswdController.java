package com.project.manager.controllers;

import com.jfoenix.controls.JFXButton;
import com.project.manager.sceneManager.SceneManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.net.URL;
import java.util.ResourceBundle;

@Component
public class ResetPasswdController implements Initializable {


    @FXML
    private JFXButton resetPasswdButton;

    @FXML
    private TextField userNameToResetPass;

    private SceneManager sceneManager;

    @Autowired
    public ResetPasswdController() {
        sceneManager = SceneManager.getInstance();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
