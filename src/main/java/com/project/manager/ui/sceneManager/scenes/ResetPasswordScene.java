package com.project.manager.ui.sceneManager.scenes;

import com.project.manager.ui.sceneManager.scenes.system.CustomSceneImpl;
import javafx.stage.Stage;

/**
 * Reset Password Scene, view steps to change a password
 */
public class ResetPasswordScene extends CustomSceneImpl {

    /**
     * Constructor to specify path to the fxml file which is view to reset password
     *
     * @param stage this is the stage of that scene
     */
    public ResetPasswordScene(Stage stage) {
        super(stage);
        super.setWindowTitle("Reset Password");
        super.setPathToFXML("/fxml/resetPassword/resetPassword.fxml");
    }
}