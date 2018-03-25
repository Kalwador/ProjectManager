package com.project.manager.data;

import com.project.manager.BCryptEncoder;
import com.project.manager.entities.Project;
import com.project.manager.entities.UserModel;
import com.project.manager.models.UserRole;
import com.project.manager.repositories.ProjectRepository;
import com.project.manager.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.HashSet;

/**
 * This is the class which is responsible for injecting test data into database.
 * This class perform injection of test data into database and relations between tables
 */
@Component
public class InjectData {

    private ProjectRepository projectRepository;

    private UserRepository userRepository;

    @Autowired
    public InjectData(ProjectRepository projectRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    /**
     * This method perform injection of test data into database and relations between tables
     */
   @PostConstruct
    public void injectData() {
        UserModel userOne = UserModel.builder()
                .username("user")
                .code(String.valueOf(new Date().getTime()))
                .password(BCryptEncoder.encode("password"))
                .email("user@mail.com")
                .role(UserRole.USER)
                .isFirstLogin(false)
                .isBlocked(false)
                .projects(new HashSet<>())
                .build();

        Project projectOne = Project.builder()
                .projectName("project1")
                .projectInformation("project1Info")
                .members(new HashSet<>())
                .build();

        Project projectTwo = Project.builder()
                .projectName("project2")
                .projectInformation("project2Info")
                .members(new HashSet<>())
                .build();

        Project projectThree = Project.builder()
                .projectName("project3")
                .projectInformation("project3Info")
                .members(new HashSet<>())
                .build();

        userOne = userRepository.save(userOne);
        projectOne = projectRepository.save(projectOne);
        projectTwo = projectRepository.save(projectTwo);
        projectThree = projectRepository.save(projectThree);

        userOne.getProjects().add(projectOne);
        userOne.getProjects().add(projectTwo);
        userOne.getProjects().add(projectThree);

        projectOne.getMembers().add(userOne);
        projectTwo.getMembers().add(userOne);
        projectThree.getMembers().add(userOne);

        userRepository.save(userOne);
        projectRepository.save(projectOne);
        projectRepository.save(projectTwo);
        projectRepository.save(projectThree);

    }

}
