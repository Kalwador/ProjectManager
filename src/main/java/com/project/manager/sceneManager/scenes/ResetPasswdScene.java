package com.project.manager.sceneManager.scenes;

import com.project.manager.sceneManager.scenes.system.CustomSceneImpl;
import javafx.stage.Stage;

public class ResetPasswdScene extends CustomSceneImpl {

    public ResetPasswdScene(Stage stage) {
        super(stage);
        super.setWindowTitle("Reset Password");
        super.setPathToFXML("/fxml/resetPasswd.fxml");
    }

}
