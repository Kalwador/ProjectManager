package com.project.manager.ui.sceneManager.scenes;

import com.project.manager.ui.sceneManager.scenes.system.CustomSceneImpl;
import javafx.stage.Stage;

/**
 * Reset Password Scene, view steps to change a password
 */
public class ResetPasswdScene extends CustomSceneImpl {

    public ResetPasswdScene(Stage stage) {
        super(stage);
        super.setWindowTitle("Reset Password");
        super.setPathToFXML("/fxml/resetPasswd.fxml");
    }

}