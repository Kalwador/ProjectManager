package com.project.manager.ui.sceneManager.scenes;

import com.project.manager.ui.sceneManager.scenes.system.CustomSceneImpl;
import javafx.stage.Stage;

public class PersonalDataScene extends CustomSceneImpl {

    /**
     * Constructor to specify path to the fxml file which is window view
     *
     * @param stage this is the stage of that scene
     */
    public PersonalDataScene(Stage stage) {
        super(stage);
        super.setWindowTitle("Account Information");
        super.setPathToFXML("/fxml/personalData/personalDataWindow.fxml");
    }
}