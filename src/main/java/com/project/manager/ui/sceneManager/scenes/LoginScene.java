package com.project.manager.ui.sceneManager.scenes;

import com.project.manager.ui.sceneManager.scenes.system.CustomSceneImpl;
import javafx.stage.Stage;

/**
 * Login Scene, first view on application start
 */
public class LoginScene extends CustomSceneImpl {

    /**
     * Constructor to specify path to the fxml file which is window view
     *
     * @param stage this is the stage of that scene
     */
    public LoginScene(Stage stage) {
        super(stage);
        super.setWindowTitle("Sign in");
        super.setPathToFXML("/fxml/login/login.fxml");
    }
}
