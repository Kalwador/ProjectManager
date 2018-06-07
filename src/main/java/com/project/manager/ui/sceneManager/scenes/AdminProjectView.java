package com.project.manager.ui.sceneManager.scenes;

import com.project.manager.ui.sceneManager.scenes.system.CustomSceneImpl;
import javafx.stage.Stage;

/**
 * This class provides view for project in admin panel
 */
public class AdminProjectView extends CustomSceneImpl {

    /**
     * Constructor to specify path to the fxml file which is window view
     *
     * @param stage this is the stage of that scene
     */
    public AdminProjectView(Stage stage) {
        super(stage);
        super.setWindowTitle("Project Details");
        super.setPathToFXML("/fxml/admin/adminProjectView.fxml");
    }
}
