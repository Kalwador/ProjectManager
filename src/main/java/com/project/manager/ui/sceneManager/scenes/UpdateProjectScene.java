package com.project.manager.ui.sceneManager.scenes;

import com.project.manager.ui.sceneManager.scenes.system.CustomSceneImpl;
import javafx.stage.Stage;

/**
 * Scene to update selected project
 */
public class UpdateProjectScene extends CustomSceneImpl {

    /**
     * Constructor to specify path to the fxml file which is update project view
     *
     * @param stage this is the stage of that scene
     */
    public UpdateProjectScene(Stage stage) {
        super(stage);
        super.setWindowTitle("Update Project");
        super.setPathToFXML("/fxml/admin/adminUpdateProject.fxml");
    }
}