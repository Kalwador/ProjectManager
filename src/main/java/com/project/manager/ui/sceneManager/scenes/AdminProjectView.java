package com.project.manager.ui.sceneManager.scenes;

import com.project.manager.ui.sceneManager.scenes.system.CustomSceneImpl;
import javafx.stage.Stage;

public class AdminProjectView extends CustomSceneImpl {
    public AdminProjectView(Stage stage) {
        super(stage);
        super.setWindowTitle("Project Details");
        super.setPathToFXML("/fxml/admin/adminProjectView.fxml");
    }
}
