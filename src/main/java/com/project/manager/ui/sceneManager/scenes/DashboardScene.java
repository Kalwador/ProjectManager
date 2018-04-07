package com.project.manager.ui.sceneManager.scenes;

import com.project.manager.ui.sceneManager.scenes.system.CustomSceneImpl;
import javafx.stage.Stage;

/**
 * Dashboard Scene, view list of projectsAsUser logged user
 */
public class DashboardScene extends CustomSceneImpl {

    public DashboardScene(Stage stage) {
        super(stage);
        super.setWindowTitle("Dashboard");
        super.setPathToFXML("/fxml/dashboard/dashboard.fxml");
    }
}
