package com.project.manager.ui.sceneManager.scenes;

import com.project.manager.ui.sceneManager.scenes.system.CustomSceneImpl;
import javafx.stage.Stage;

public class FirstLoginScene extends CustomSceneImpl {
    /**
     * Constructor to specify path to the fxml file which is window view
     *
     * @param stage this is the stage of that scene
     */
    public FirstLoginScene(Stage stage) {
        super(stage);
        super.setWindowTitle("First Login");
        super.setPathToFXML("/fxml/login/firstLogin.fxml");
    }
}
