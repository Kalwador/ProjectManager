package com.project.manager.ui.sceneManager.scenes;

import com.project.manager.ui.sceneManager.scenes.system.CustomSceneImpl;
import javafx.stage.Stage;

/**
 * Scene to update selected project
 */
public class UpdateProjectScene extends CustomSceneImpl {

    /**
     * Constructor to specify path to the fxml file which is window view
     *
     * @param primaryStage this is the mail stage of application
     */
    public UpdateProjectScene(Stage primaryStage) {
        super(primaryStage);
        super.setWindowTitle("Update Project");
        super.setPathToFXML("/fxml/admin/adminUpdateProject.fxml");
    }
}