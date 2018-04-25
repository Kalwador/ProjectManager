package com.project.manager.ui.sceneManager.scenes;

import com.project.manager.ui.sceneManager.scenes.system.CustomSceneImpl;
import javafx.stage.Stage;

/**
 * Scene of generate report
 */
public class GenerateReportScene extends CustomSceneImpl {

    public GenerateReportScene(Stage stage) {
        super(stage);
        super.setWindowTitle("Generate rapport");
        super.setPathToFXML("/fxml/generateReportWindow.fxml");
    }
}