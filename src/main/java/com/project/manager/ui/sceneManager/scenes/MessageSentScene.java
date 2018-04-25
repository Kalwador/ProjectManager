package com.project.manager.ui.sceneManager.scenes;

import com.project.manager.ui.sceneManager.scenes.system.CustomSceneImpl;
import javafx.stage.Stage;

public class MessageSentScene extends CustomSceneImpl {
    public MessageSentScene(Stage primaryStage) {
        super(primaryStage);
        super.setWindowTitle("Sent Message");
        super.setPathToFXML("/fxml/sentMessageWindow.fxml");
    }
}
