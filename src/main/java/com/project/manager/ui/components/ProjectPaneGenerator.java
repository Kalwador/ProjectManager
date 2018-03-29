package com.project.manager.ui.components;

import com.project.manager.controllers.ProjectPaneController;
import com.project.manager.services.ProjectService;
import com.project.manager.services.SessionService;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * This is the class which is responsible for projectsAsUser panes in Dashboard view.
 * This class perform generating of projectsAsUser panes into Dashboard view.
 */
@Component
public class ProjectPaneGenerator {

    private ProjectService projectService;

    private SessionService sessionService;


    @Autowired
    public ProjectPaneGenerator(ProjectService projectService) {
        this.projectService = projectService;
        this.sessionService = SessionService.getInstance();
    }

    /**
     * This method perform generating of projectsAsUser panes witch will be displayed into Dashboard
     * @param projectsArea
     * @exception IOException
     */
    public void createPanes(VBox projectsArea) {
        projectService.projectsOfUser().forEach(project -> {
            try {

                AnchorPane newAnchorPane;

                ProjectPaneController controller = new ProjectPaneController();

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/projectPane.fxml"));

                fxmlLoader.setController(controller);

                newAnchorPane = fxmlLoader.load();

                controller.setProjectId(project.getId());

                controller.getProjectName().setText(project.getProjectName());

                projectsArea.getChildren().add(newAnchorPane);

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
