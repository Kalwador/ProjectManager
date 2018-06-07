package com.project.manager.ui.sceneManager.scenes;

import com.project.manager.ui.sceneManager.scenes.system.CustomSceneImpl;
import javafx.stage.Stage;

/**
 * Project View Scene, view of selected project from Dashboard
 */
public class ManagerProjectViewScene extends CustomSceneImpl {

    /**
     * Constructor to specify path to the fxml file which is window view
     *
     * @param stage this is the stage of that scene
     */
    public ManagerProjectViewScene(Stage stage) {
        super(stage);
        super.setWindowTitle("Manager Project View");
        super.setPathToFXML("/fxml/manager/managerProjectView.fxml");
    }
}
