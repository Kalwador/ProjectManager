package com.project.manager.sceneManager.scenes;

import com.project.manager.sceneManager.scenes.system.CustomSceneImpl;
import javafx.stage.Stage;

/**
 * Scene of admin with his dashboard containing all admin functionality
 */
public class AdminDashboardScene extends CustomSceneImpl {

    /**
     * Constructor to specify path to the fxml file which is window view
     * @param stage this is the stage of that scene
     */
    public AdminDashboardScene(Stage stage) {
        super(stage);
        super.setWindowTitle("Admin Dashboard");
        super.setPathToFXML("/fxml/adminDashboard.fxml");
    }
}