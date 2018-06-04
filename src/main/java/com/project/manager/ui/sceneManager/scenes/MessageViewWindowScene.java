package com.project.manager.ui.sceneManager.scenes;

import com.project.manager.ui.sceneManager.scenes.system.CustomSceneImpl;
import javafx.stage.Stage;

/**
 * This class provides view for every selected message
 */
public class MessageViewWindowScene extends CustomSceneImpl {

    /**
     * Constructor to specify path to the fxml file which is view to show selected message
     *
     * @param stage this is the stage of that scene
     */
    public MessageViewWindowScene(Stage stage) {
        super(stage);
        super.setWindowTitle("Message");
        super.setPathToFXML("/fxml/messages/messageViewWindow.fxml");
    }
}