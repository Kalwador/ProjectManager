package com.project.manager.data;

import com.project.manager.BCryptEncoder;
import com.project.manager.entities.Message;
import com.project.manager.entities.Project;
import com.project.manager.entities.UserModel;
import com.project.manager.models.UserRole;
import com.project.manager.repositories.MessageRepository;
import com.project.manager.repositories.ProjectRepository;
import com.project.manager.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
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

    private MessageRepository messageRepository;

    @Autowired
    public InjectData(ProjectRepository projectRepository,
                      UserRepository userRepository,
                      MessageRepository messageRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
    }

    /**
     * This method perform injection of test data into database and relations between tables
     */
    @PostConstruct
    public void injectData() {

        UserModel client = UserModel.builder()
                .username("client")
                .unlockCode(String.valueOf(new Date().getTime()))
                .password(BCryptEncoder.encode("password"))
                .email("client@mail.com")
                .role(UserRole.USER)
                .isLocked(false)
                .projectsAsClient(new HashSet<>())
                .firstName("Benek")
                .lastName("Bebenek")
                .build();

        UserModel manager = UserModel.builder()
                .username("manager")
                .unlockCode(String.valueOf(new Date().getTime()))
                .password(BCryptEncoder.encode("password"))
                .email("manager@mail.com")
                .role(UserRole.USER)
                .unlockPasswdCode(null)
                .isLocked(false)
                .projectsAsClient(new HashSet<>())
                .firstName("Edward")
                .lastName("Oncki")
                .projectsAsManager(new HashSet<>())
                .build();

        for (int i = 0; i < 10; i++) {
            UserModel userOne = UserModel.builder()
                    .username("user" + i)
                    .unlockCode(String.valueOf(new Date().getTime()))
                    .password(BCryptEncoder.encode("password"))
                    .email("user" + i + "@mail.com")
                    .role(UserRole.USER)
                    .isLocked(false)
                    .projectsAsUser(new HashSet<>())
                    .firstName("Adam" + i)
                    .lastName("Spadam" + i)
                    .build();

            Project projectOne = Project.builder()
                    .projectName("project" + i)
                    .projectInformation("project " + "Info")
                    .members(new HashSet<>())
                    .client(client)
                    .build();

//        Project projectTwo = Project.builder()
//                .projectName("project2")
//                .projectInformation("project2Info")
//                .members(new HashSet<>())
//                .build();
//
//        Project projectThree = Project.builder()
//                .projectName("project3")
//                .projectInformation("project3Info")
//                .members(new HashSet<>())
//                .build();

            userOne = userRepository.save(userOne);
            client = userRepository.save(client);
            manager = userRepository.save(manager);
            projectOne = projectRepository.save(projectOne);
//        projectTwo = projectRepository.save(projectTwo);
//        projectThree = projectRepository.save(projectThree);

            userOne.getProjectsAsUser().add(projectOne);
//        userOne.getProjectsAsUser().add(projectTwo);
//        userOne.getProjectsAsUser().add(projectThree);

            client.getProjectsAsClient().add(projectOne);

            manager.getProjectsAsManager().add(projectOne);

            projectOne.getMembers().add(userOne);
//        projectTwo.getMembers().add(userOne);
//        projectThree.getMembers().add(userOne);

            projectOne.setClient(client);

            projectOne.setManager(manager);

            userRepository.save(userOne);
            userRepository.save(client);
            userRepository.save(manager);
            projectRepository.save(projectOne);
//        projectRepository.save(projectTwo);
//        projectRepository.save(projectThree);
        }


        UserModel admin = UserModel
                .builder()
                .username("admin")
                .password(BCryptEncoder.encode("admin"))
                .email("admin@mail.com")
                .role(UserRole.ADMIN)
                .isLocked(false)
                .unlockCode(String.valueOf(new Date().getTime()))
                .messages(new HashSet<>())
                .build();

        UserModel sender = UserModel
                .builder()
                .firstName("Sender")
                .lastName("Messager")
                .username("sender123")
                .email("sender123@mail.com")
                .password(BCryptEncoder.encode("password"))
                .isLocked(false)
                .role(UserRole.USER)
                .unlockCode(String.valueOf(new Date().getTime()))
                .messages(new HashSet<>())
                .build();

        Message sentMessage = Message
                .builder()
                .sender(admin.getEmail())
                .receiver(sender.getEmail())
                .title("Msg sent by admin to user")
                .contents("Message which will sent by admin to user")
                .sentDate(LocalDateTime.now())
                .users(new HashSet<>())
                .build();

        Message receivedMessage = Message
                .builder()
                .sender(sender.getEmail())
                .receiver(admin.getEmail())
                .title("Msg sent by user to admin")
                .contents("Message which will sent by user to admin")
                .sentDate(LocalDateTime.now())
                .users(new HashSet<>())
                .build();

        admin = userRepository.save(admin);
        sender = userRepository.save(sender);

        sentMessage = messageRepository.save(sentMessage);
        receivedMessage = messageRepository.save(receivedMessage);

        admin.getMessages().add(sentMessage);
        sentMessage.getUsers().add(admin);
        admin.getMessages().add(receivedMessage);
        receivedMessage.getUsers().add(admin);

        sender.getMessages().add(sentMessage);
        sentMessage.getUsers().add(sender);
        sender.getMessages().add(receivedMessage);
        receivedMessage.getUsers().add(sender);

        userRepository.save(admin);
        userRepository.save(sender);

        messageRepository.save(sentMessage);
        messageRepository.save(receivedMessage);
    }
}
