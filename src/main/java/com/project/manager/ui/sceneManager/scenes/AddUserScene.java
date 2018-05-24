package com.project.manager.ui.sceneManager.scenes;

import com.project.manager.ui.sceneManager.scenes.system.CustomSceneImpl;
import javafx.stage.Stage;

/**
 * This stage provides view to add specified user
 */
public class AddUserScene extends CustomSceneImpl {

    /**
     * Constructor to specify path to the fxml file which is window view
     *
     * @param stage this is the stage of that scene
     */
    public AddUserScene(Stage stage) {
        super(stage);
        super.setWindowTitle("Dashboard");
        super.setPathToFXML("/fxml/userSelector.fxml");
    }
}