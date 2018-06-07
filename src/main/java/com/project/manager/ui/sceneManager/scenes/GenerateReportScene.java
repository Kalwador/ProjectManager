package com.project.manager.ui.sceneManager.scenes;

import com.project.manager.ui.sceneManager.scenes.system.CustomSceneImpl;
import javafx.stage.Stage;

/**
 * Scene of generate report
 */
public class GenerateReportScene extends CustomSceneImpl {

    /**
     * Constructor to specify path to the fxml file which is window view
     *
     * @param stage this is the stage of that scene
     */
    public GenerateReportScene(Stage stage) {
        super(stage);
        super.setWindowTitle("Generate rapport");
        super.setPathToFXML("/fxml/generateReportWindow.fxml");
    }
}