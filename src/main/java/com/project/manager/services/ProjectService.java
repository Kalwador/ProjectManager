package com.project.manager.services;

import com.project.manager.entities.Project;
import com.project.manager.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * This is the class which is responsible for returning list of projectsAsUser logged user.
 * This class perform method projectsOfUser which returning list of projectsAsUser by logged user id
 */
@Service
public class ProjectService {

    private ProjectRepository projectRepository;

    private SessionService sessionService;

    @Autowired
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
        this.sessionService = SessionService.getInstance();
    }

    /**
     * This method perform returning list of projectsAsUser by logged user id
     * @return list of projectsAsUser of logged user by id
     */
    public List<Project> projectsOfUser() {
        return projectRepository.findByMembers_Id(sessionService.getID()).get();
    }

    /**
     * This method are returning all project form DB
     * @return list of {@link Project}
     */
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    /**
     * TO IMPLEMENT
     * @param id
     */
    public void delete(long id) {
    }
}
