package com.project.manager.ui.sceneManager.scenes;

import com.project.manager.ui.sceneManager.scenes.system.CustomSceneImpl;
import javafx.stage.Stage;

public class MessageViewWindowScene extends CustomSceneImpl {

    /**
     * Constructor to specify path to the fxml file which is window view
     *
     * @param stage this is the stage of that scene
     */
    public MessageViewWindowScene(Stage stage) {
        super(stage);
        super.setWindowTitle("Message");
        super.setPathToFXML("/fxml/messageViewWindow.fxml");
    }
}