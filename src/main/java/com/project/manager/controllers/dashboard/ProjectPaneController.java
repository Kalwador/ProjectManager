package com.project.manager.controllers.dashboard;

import com.jfoenix.controls.JFXButton;
import com.project.manager.config.ApplicationContextProvider;
import com.project.manager.entities.Project;
import com.project.manager.exceptions.project.ProjectNotExistException;
import com.project.manager.services.ProjectService;
import com.project.manager.services.SessionService;
import com.project.manager.ui.sceneManager.SceneManager;
import com.project.manager.ui.sceneManager.SceneType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * This is the class which is responsible for each pane view with project name.
 * This class perform display project view after clicked view button on one of user projects on dashboard.
 */
@Getter
@Setter
@Log4j
public class ProjectPaneController implements Initializable {

    @FXML
    private JFXButton viewProject;

    private Long projectId;
    private SessionService session;
    private ProjectService projectService;
    private SceneManager sceneManager;

    public ProjectPaneController() {
        this.session = SessionService.getInstance();
        this.sceneManager = SceneManager.getInstance();
        projectService = ApplicationContextProvider.getInstance().getContext().getBean(ProjectService.class);
    }

    /**
     * Initialization of project view after clicked view button
     *
     * @param location  URL location
     * @param resources Bundle resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        viewProject.setOnAction(e -> {
            try {
                loadProjectView();
            } catch (ProjectNotExistException ex) {
                log.warn("Problem with loading project scene, project with passed id was not found!");
                ex.printStackTrace();
            }
        });
    }

    private void loadProjectView() throws ProjectNotExistException {
        Project project = projectService.getProjectById(projectId);
        session.setProject(project);

        if (project.getManager().getId().equals(session.getUserModel().getId())) {
            sceneManager.showScene(SceneType.MANAGER_PROJECT_VIEW);
        } else {
            sceneManager.showScene(SceneType.EMPLOYEE_PROJECT_VIEW);
        }
    }
}
