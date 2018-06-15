package com.project.manager.ui.sceneManager.scenes;

import com.project.manager.ui.sceneManager.scenes.system.CustomSceneImpl;
import javafx.stage.Stage;

public class NewProjectScene extends CustomSceneImpl {
    public NewProjectScene(Stage primaryStage) {
        super(primaryStage);
        super.setWindowTitle("New Project");
        super.setPathToFXML("/fxml/admin/adminCreateNewProject.fxml");
    }
}
