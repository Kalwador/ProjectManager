package com.project.manager.ui.components;

import com.project.manager.controllers.dashboard.ProjectPaneController;
import com.project.manager.entities.Project;
import com.project.manager.services.SessionService;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * This is the class which is responsible for projectsAsUser panes in Dashboard view.
 * This class perform generating of projectsAsUser panes into Dashboard view.
 */
@Component
public class ProjectPaneGenerator {

    private SessionService sessionService;
    private Logger logger;

    @Autowired
    public ProjectPaneGenerator() {
        this.sessionService = SessionService.getInstance();
        this.logger = Logger.getLogger(ProjectPaneGenerator.class);
    }

    /**
     * This method perform generating of projectsAsUser panes witch will be displayed into Dashboard
     *
     * @param projectsArea //TODO
     */
    public void createPanes(VBox projectsArea) {
        sessionService.getUserModel().getProjectsAsManager().forEach(project -> {
            createProjectBrick(projectsArea, project);
        });
        sessionService.getUserModel().getProjectsAsUser().forEach(project -> {
            createProjectBrick(projectsArea, project);
        });
    }

    /**
     * //TODO MACIEK
     *
     * @param projectsArea //TODO MACIEK
     * @param project      //TODO MACIEK
     */
    private void createProjectBrick(VBox projectsArea, Project project) {
        try {
            AnchorPane newAnchorPane;
            ProjectPaneController controller = new ProjectPaneController();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/dashboard/projectPane.fxml"));
            fxmlLoader.setController(controller);
            newAnchorPane = fxmlLoader.load();
            controller.setProjectId(project.getId());
            controller.getViewProject().setText(project.getProjectName());
            projectsArea.getChildren().add(newAnchorPane);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("Project pane fml not found or problem with loading it");
        }
    }
}
