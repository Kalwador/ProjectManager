package com.project.manager.ui.sceneManager.scenes;

import com.project.manager.ui.sceneManager.scenes.system.CustomSceneImpl;
import javafx.stage.Stage;

/**
 * This is the class which provides personal data view
 */
public class PersonalDataScene extends CustomSceneImpl {

    /**
     * Constructor to specify path to the fxml file which is personal data of each user
     *
     * @param stage this is the stage of that scene
     */
    public PersonalDataScene(Stage stage) {
        super(stage);
        super.setWindowTitle("Account Information");
        super.setPathToFXML("/fxml/personalData/personalDataWindow.fxml");
    }
}