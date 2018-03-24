package com.project.manager.sceneManager.scenes;

import com.project.manager.sceneManager.scenes.system.CustomSceneImpl;
import javafx.stage.Stage;

/**
 * Scene to update selected project
 */
public class AdminUpdateProjectScene extends CustomSceneImpl {

    /**
     * Constructor to specify path to the fxml file which is window view
     * @param primaryStage this is the mail stage of application
     */
    public AdminUpdateProjectScene(Stage primaryStage) {
            super(primaryStage);
            super.setWindowTitle("Update Window");
            super.setPathToFXML("/fxml/adminUpdateProject.fxml");
    }
}
