package com.project.manager.ui.sceneManager.scenes;

import com.project.manager.ui.sceneManager.scenes.system.CustomSceneImpl;
import javafx.stage.Stage;

/**
 * Reset Password Scene, view steps to change a password
 */
public class ResetPasswordScene extends CustomSceneImpl {

    public ResetPasswordScene(Stage stage) {
        super(stage);
        super.setWindowTitle("Reset Password");
        super.setPathToFXML("/fxml/resetPassword/resetPassword.fxml");
    }
}