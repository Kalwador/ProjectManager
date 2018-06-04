package com.project.manager.ui.sceneManager.scenes;

import com.project.manager.ui.sceneManager.scenes.system.CustomSceneImpl;
import javafx.stage.Stage;

/**
 * This class provide scene of registration
 */
public class RegistrationScene extends CustomSceneImpl {

    /**
     * Constructor to specify path to the fxml file which is registration view
     *
     * @param stage this is the stage of that scene
     */
    public RegistrationScene(Stage stage) {
        super(stage);
        super.setWindowTitle("Sign up");
        super.setPathToFXML("/fxml/registration/registration.fxml");
    }
}