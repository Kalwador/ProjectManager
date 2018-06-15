package com.project.manager.ui.sceneManager.scenes;

import com.project.manager.ui.sceneManager.scenes.system.CustomSceneImpl;
import javafx.stage.Stage;

/**
 * This stage provides view to add new task into project
 */
public class AddTaskScene extends CustomSceneImpl {

    /**
     * Constructor to specify path to the fxml file which is window view
     *
     * @param stage this is the stage of that scene
     */
    public AddTaskScene(Stage stage) {
        super(stage);
        super.setWindowTitle("Adding new task");
        super.setPathToFXML("/fxml/task/taskAddingWindow.fxml");
    }
}
