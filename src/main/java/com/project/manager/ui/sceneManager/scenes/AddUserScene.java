package com.project.manager.ui.sceneManager.scenes;

import com.project.manager.ui.sceneManager.scenes.system.CustomSceneImpl;
import javafx.stage.Stage;

public class AddUserScene extends CustomSceneImpl {

    public AddUserScene(Stage stage) {
        super(stage);
        super.setWindowTitle("Dashboard");
        super.setPathToFXML("/fxml/userSelector.fxml");
    }
}