package com.project.manager.ui.sceneManager.scenes;

import com.project.manager.ui.sceneManager.scenes.system.CustomSceneImpl;
import javafx.stage.Stage;

/**
 * Project View Scene, view of selected project from Dashboard
 */
public class EmployeeProjectView extends CustomSceneImpl {

    /**
     * Constructor to specify path to the fxml file which is window view
     *
     * @param stage this is the stage of that scene
     */
    public EmployeeProjectView(Stage stage) {
        super(stage);
        super.setWindowTitle("Employee Project View");
        super.setPathToFXML("/fxml/employee/employeeProjectView.fxml");
    }
}
