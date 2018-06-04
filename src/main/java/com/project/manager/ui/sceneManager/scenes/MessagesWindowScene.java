package com.project.manager.ui.sceneManager.scenes;

import com.project.manager.ui.sceneManager.scenes.system.CustomSceneImpl;
import javafx.stage.Stage;

/**
 * Scene to show all messages and manage them
 */
public class MessagesWindowScene extends CustomSceneImpl {

    /**
     * Constructor to specify path to the fxml file which is window view
     *
     * @param stage this is the stage of that scene
     */
    public MessagesWindowScene(Stage stage) {
        super(stage);
        super.setWindowTitle("Sent Message");
        super.setPathToFXML("/fxml/messages/messagesWindow.fxml");
    }
}
