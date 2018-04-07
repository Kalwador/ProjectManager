package com.project.manager.ui.sceneManager.scenes;

import com.project.manager.ui.sceneManager.scenes.system.CustomSceneImpl;
import javafx.stage.Stage;

/**
 * Project View Scene, view of selected project from Dashboard
 */
public class ManagerProjectView extends CustomSceneImpl {

    public ManagerProjectView(Stage stage) {
        super(stage);
        super.setWindowTitle("Manager Project View");
        super.setPathToFXML("/fxml/manager/managerProjectView.fxml");
    }
}
