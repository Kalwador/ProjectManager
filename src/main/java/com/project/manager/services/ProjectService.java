package com.project.manager.services;

import com.project.manager.entities.Project;
import com.project.manager.entities.UserModel;
import com.project.manager.exceptions.project.ProjectNotExistException;
import com.project.manager.models.ProjectTableView;
import com.project.manager.repositories.ProjectRepository;
import com.project.manager.repositories.UserRepository;
import com.project.manager.ui.AlertManager;
import com.project.manager.ui.components.MemberPaneGenerator;
import com.project.manager.ui.components.admin.AdminDashboardTablesComponent;
import javafx.beans.binding.LongExpression;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * This is the class which is responsible for returning list of projectsAsUser logged user.
 * This class perform method projectsOfUser which returning list of projectsAsUser by logged user id
 */
@Log4j
@Service
public class ProjectService {

    private ProjectRepository projectRepository;
    private UserRepository userRepository;

    private SessionService sessionService;
    private MemberPaneGenerator memberPaneGenerator;

    @Autowired
    public ProjectService(ProjectRepository projectRepository,
                          MemberPaneGenerator memberPaneGenerator,
                          UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.sessionService = SessionService.getInstance();
        this.memberPaneGenerator = memberPaneGenerator;
        this.userRepository = userRepository;
    }

    /**
     * This method perform returning list of projectsAsUser by logged user id
     *
     * @return list of projectsAsUser of logged user by id
     */
    public List<Project> projectsOfUser() {
        return projectRepository.findByMembers_Id(sessionService.getUserModel().getId()).get();
    }

    /**
     * This method are returning all project form DB
     *
     * @return list of {@link Project}
     */
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public void getProjectToUpdate() {
        Long id = AdminDashboardTablesComponent.projectTableViews
                .stream()
                .filter(projectViewInTable -> projectViewInTable.getCheck().get().isSelected())
                .map(ProjectTableView::getId)
                .map(LongExpression::getValue)
                .findFirst()
                .get();
        Optional<Project> project = projectRepository.findById(id);
        sessionService.setProject(project.get());
    }

    public void getProjectToShow() {
        Long id = AdminDashboardTablesComponent.projectTableViews
                .stream()
                .filter(projectViewInTable -> projectViewInTable.getCheck().get().isSelected())
                .map(ProjectTableView::getId)
                .map(LongExpression::getValue)
                .findFirst()
                .get();
        Optional<Project> project = projectRepository.findById(id);
        sessionService.setProject(project.get());
    }

    /**
     * This method perform delete project operation on projectRepository by inserted project Id in parameter
     *
     * @param projectId Id of project that we need to delete
     */
    public void delete(Long projectId) throws ProjectNotExistException {
        Optional<Project> optionalProject = projectRepository.findById(projectId);
        if (optionalProject.isPresent()) {
            Alert alert = AlertManager.showConfirmationAlert("Delete", "Do you really want to delete " +
                    "that project?");
            if (alert.getResult().equals(ButtonType.OK)) {
                projectRepository.delete(optionalProject.get());
            }
        } else {
            log.warn("User ask about project which does not exist - project id : " + projectId);
            throw new ProjectNotExistException();
        }
    }

    /**
     * This method perform delete list of projects operation on projectRepository by inserted list of project indexes
     * in parameter.
     *
     * @param indexes indexes of projects that we need to delete
     */
    public void delete(List<Long> indexes) {
        Alert alert = AlertManager.showConfirmationAlert("Delete", "Do you really want to delete" +
                " this " + indexes.size() + " project(s)?");
        if (alert.getResult().equals(ButtonType.OK)) {
            for (Long id : indexes) {
                projectRepository.delete(projectRepository.findById(id).get());
            }
        }
    }

    /**
     * This method are returning project form DB by project Id in parameter
     *
     * @param projectId Id of project that we need to get
     * @return {@link Project}
     */
    public Project getProjectById(Long projectId) throws ProjectNotExistException {
        Optional<Project> optionalProject = this.projectRepository.findById(projectId);
        if (optionalProject.isPresent()) return optionalProject.get();
        throw new ProjectNotExistException();
    }

    /**
     * This method perform update name of project operation on projectRepository by inserted model of project
     * and new project name
     *
     * @param project     model of project that we need to update
     * @param projectName new name of project
     */
    public void updateName(Project project, String projectName) {
        project.setProjectName(projectName);
        projectRepository.save(project);
    }

    /**
     * This method perform update description of project operation on projectRepository by inserted model of project
     * and new project description
     *
     * @param project            model of project that we need to update
     * @param projectDescription new description of project
     */
    public void updateDescription(Project project, String projectDescription) {
        project.setProjectInformation(projectDescription);
        projectRepository.save(project);
    }

    /**
     * This method perform update name of project operation on projectRepository by inserted model of project and
     * new model of manager
     *
     * @param project model of project that we need to update
     * @param manager model of new manager of project
     */
    public void updateManager(Project project, UserModel manager) {
        if (Optional.ofNullable(manager).isPresent()) {
            project.setManager(manager);
            projectRepository.save(project);
        }
    }

    /**
     * This method perform update members of project operation on projectRepository by inserted model of project and
     * new list of members
     *
     * @param project model of project that we need to update
     */
    public void updateMembers(Project project) {
        Set<UserModel> oldMembers = project.getMembers();
        Set<UserModel> newMembers = new HashSet<>(memberPaneGenerator.getMembers());

        for (UserModel oldUser : oldMembers) {
            if (!newMembers.contains(oldUser)) {
                oldUser.getProjectsAsUser().remove(project);
                userRepository.save(oldUser);
            }
        }

        for (UserModel newUser : newMembers) {
            if (!project.getMembers().contains(newUser)) {
                newUser.getProjectsAsUser().add(project);
                userRepository.save(newUser);
            }
        }
    }
}