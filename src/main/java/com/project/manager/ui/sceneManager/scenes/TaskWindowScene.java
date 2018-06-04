package com.project.manager.ui.sceneManager.scenes;

import com.project.manager.ui.sceneManager.scenes.system.CustomSceneImpl;
import javafx.stage.Stage;

/**
 * This scene shows all information about task
 */
public class TaskWindowScene extends CustomSceneImpl {

    /**
     * Constructor to specify path to the fxml file which is window view
     *
     * @param stage this is the stage of that scene
     */
    public TaskWindowScene(Stage stage) {
        super(stage);
        super.setWindowTitle("Task View");
        super.setPathToFXML("/fxml/task/taskWindow.fxml");
    }
}
