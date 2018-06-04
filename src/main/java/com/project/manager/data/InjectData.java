package com.project.manager.data;

import com.google.common.collect.Sets;
import com.project.manager.entities.Message;
import com.project.manager.entities.Project;
import com.project.manager.entities.Task;
import com.project.manager.entities.UserModel;
import com.project.manager.models.UserRole;
import com.project.manager.models.task.TaskPriority;
import com.project.manager.models.task.TaskStatus;
import com.project.manager.repositories.MessageRepository;
import com.project.manager.repositories.ProjectRepository;
import com.project.manager.repositories.TaskRepository;
import com.project.manager.repositories.UserRepository;
import com.project.manager.utils.BCryptEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;

/**
 * This is the class which is responsible for injecting test data into database.
 * This class perform injection of test data into database and relations between tables
 */
@Component
public class InjectData {

    private ProjectRepository projectRepository;
    private TaskRepository taskRepository;
    private UserRepository userRepository;
    private MessageRepository messageRepository;

    private UserModel user;
    private UserModel user2;
    private UserModel manager;
    private UserModel admin;

    private Project projectOne;
    private Project projectTwo;
    private Project projectThree;

    private Task task1;
    private Task task2;
    private Task task3;
    private Task task4;
    private Task task5;
    private Task task6;
    private Task task7;
    private Task task8;
    private Task task9;
    private Task task10;

    private Message sentMessage;
    private Message receivedMessage;
    private Message sentMessage2;
    private Message receivedMessage2;
    private InjectAvatar injectAvatar;

    @Autowired
    public InjectData(ProjectRepository projectRepository,
                      TaskRepository taskRepository,
                      UserRepository userRepository,
                      MessageRepository messageRepository,
                      InjectAvatar injectAvatar) {
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
        this.injectAvatar = injectAvatar;
    }

    /**
     * This method perform injection of test data into database and relations between tables
     */
    @PostConstruct
    public void injectData() {
        addUsers();
        addProjects();
        addTasks();
        addMessages();
    }

    private void addUsers() {
        this.user = UserModel.builder()
                .username("user")
                .password(BCryptEncoder.encode("password"))
                .email("users@grr.la")
                .firstName("Benek")
                .lastName("Bebenek")
                .role(UserRole.USER)
                .isLocked(false)
                .isBlocked(false)
                .projectsAsManager(new HashSet<>())
                .projectsAsUser(new HashSet<>())
                .messages(new HashSet<>())
                .avatar(injectAvatar.getAvatar())
                .tasks(new HashSet<>())
                .build();

        this.user2 = UserModel.builder()
                .username("userTwo")
                .password(BCryptEncoder.encode("password"))
                .email("aga@grr.la")
                .firstName("Aga")
                .lastName("Lamaga")
                .role(UserRole.USER)
                .isLocked(false)
                .isBlocked(false)
                .projectsAsManager(new HashSet<>())
                .projectsAsUser(new HashSet<>())
                .messages(new HashSet<>())
                .avatar(injectAvatar.getAvatar())
                .tasks(new HashSet<>())
                .build();

        this.manager = UserModel.builder()
                .username("manager")
                .password(BCryptEncoder.encode("password"))
                .email("manager@grr.la")
                .firstName("Edward")
                .lastName("Oncki")
                .role(UserRole.USER)
                .isLocked(false)
                .isBlocked(false)
                .projectsAsManager(new HashSet<>())
                .projectsAsUser(new HashSet<>())
                .messages(new HashSet<>())
                .avatar(injectAvatar.getAvatar())
                .tasks(new HashSet<>())
                .build();

        this.admin = UserModel
                .builder()
                .username("admin")
                .password(BCryptEncoder.encode("password"))
                .email("admin@grr.la")
                .firstName("Adam")
                .lastName("Admiński")
                .role(UserRole.ADMIN)
                .isLocked(false)
                .isBlocked(false)
                .messages(new HashSet<>())
                .avatar(injectAvatar.getAvatar())
                .build();

        this.user = userRepository.save(this.user);
        this.user2 = userRepository.save(this.user2);
        this.manager = userRepository.save(this.manager);
        this.admin = userRepository.save(this.admin);

        for (int i = 0; i < 10; i++) {
            UserModel tempUser = UserModel.builder()
                    .username("users" + i)
                    .password(BCryptEncoder.encode("password"))
                    .email("users" + i + "@grr.la")
                    .firstName("Adam" + i)
                    .lastName("Spadam" + i)
                    .role(UserRole.USER)
                    .isLocked(false)
                    .isBlocked(false)
                    .projectsAsUser(new HashSet<>())
                    .projectsAsManager(new HashSet<>())
                    .messages(new HashSet<>())
                    .tasks(new HashSet<>())
                    .avatar(injectAvatar.getAvatar())
                    .build();
            tempUser = userRepository.save(tempUser);
        }
    }

    private void addProjects() {
        this.projectOne = Project.builder()
                .projectName("Lotnisko")
                .projectInformation("project1Info")
                .members(new HashSet<>())
                .tasks(new HashSet<>())
                .build();

        this.projectTwo = Project.builder()
                .projectName("project2")
                .projectInformation("project2Info")
                .members(new HashSet<>())
                .tasks(new HashSet<>())
                .build();

        this.projectThree = Project.builder()
                .projectName("project3")
                .projectInformation("project3Info")
                .members(new HashSet<>())
                .tasks(new HashSet<>())
                .build();

        this.user.getProjectsAsUser().add(this.projectOne);
        this.user2.getProjectsAsUser().add(this.projectOne);
        this.user.getProjectsAsUser().add(this.projectTwo);
        this.user.getProjectsAsUser().add(this.projectThree);

        this.manager.getProjectsAsManager().add(this.projectOne);
        this.manager.getProjectsAsManager().add(this.projectTwo);
        this.manager.getProjectsAsManager().add(this.projectThree);

        this.projectOne.setManager(this.manager);
        this.projectTwo.setManager(this.manager);
        this.projectThree.setManager(this.manager);

        this.projectOne = projectRepository.save(projectOne);
        this.projectTwo = projectRepository.save(projectTwo);
        this.projectThree = projectRepository.save(projectThree);

        this.user = userRepository.save(this.user);
        this.user2 = userRepository.save(this.user2);
        this.manager = userRepository.save(this.manager);
        this.admin = userRepository.save(this.admin);

        for (int i = 0; i < 10; i++) {
            Project tempProject = Project.builder()
                    .projectName("Temp project " + i)
                    .projectInformation("project " + i + " details")
                    .members(new HashSet<>())
                    .tasks(new HashSet<>())
                    .manager(this.manager)
                    .build();
            tempProject = projectRepository.save(tempProject);
            this.manager.getProjectsAsManager().add(tempProject);
            this.manager = userRepository.save(manager);
        }
    }

    private void addTasks() {
        this.task1 = Task.builder()
                .name("Stworzenie szkieletu")
                .description("Podstawowy szkielet funkcionalnej aplikacji")
                .taskStatus(TaskStatus.DONE)
                .tag("START")
                .taskPriority(TaskPriority.HIGH)
                .users(Sets.newHashSet(user))
                .deadline(LocalDate.of(2018,6, 12))
                .build();
        this.task2 = Task.builder()
                .name("Baza danych")
                .description("Dodanie do projektu połaczenia z baza danych")
                .taskStatus(TaskStatus.DONE)
                .tag("BD")
                .taskPriority(TaskPriority.HIGH)
                .users(Sets.newHashSet(manager))
                .deadline(LocalDate.of(2018,6, 17))
                .build();
        this.task3 = Task.builder()
                .name("Entity")
                .description("Stworznie wszystkich wymaganych entity na potrzeby projektu")
                .taskStatus(TaskStatus.CODE_REVIEW)
                .tag("BD")
                .taskPriority(TaskPriority.MEDIUM)
                .users(Sets.newHashSet(user))
                .deadline(LocalDate.of(2018,6, 3))
                .build();
        this.task4 = Task.builder()
                .name("Połaczenie z bazą")
                .description("Stworznie repozytowrów JPA dla entity oraz wykonanie testów zapisu do bazy dancyh")
                .taskStatus(TaskStatus.TESTING)
                .tag("JP")
                .taskPriority(TaskPriority.MEDIUM)
                .users(Sets.newHashSet(manager))
                .deadline(LocalDate.of(2018,6, 29))
                .build();
        this.task5 = Task.builder()
                .name("Logika aplikacji")
                .description("stworznie potrzebnej logiki aplikacji do zarazdzania i kontrolą lotów na lotnisku")
                .taskStatus(TaskStatus.TESTING)
                .tag("SERVICE")
                .taskPriority(TaskPriority.HIGH)
                .users(Sets.newHashSet(user))
                .deadline(LocalDate.of(2018,6, 19))
                .build();
        this.task6 = Task.builder()
                .name("kontrolery")
                .description("Dodanie potrzebnych kontrolerów")
                .taskStatus(TaskStatus.IN_PROGRESS)
                .tag("REST")
                .taskPriority(TaskPriority.MEDIUM)
                .users(Sets.newHashSet(manager))
                .deadline(LocalDate.of(2018,6, 11))
                .build();
        this.task7 = Task.builder()
                .name("testownie")
                .description("Testownie integracyjne bazy andych z logiką oraz kontrolerami")
                .taskStatus(TaskStatus.SPRINT_BACKLOG)
                .tag("[TEST]")
                .taskPriority(TaskPriority.LOW)
                .users(Sets.newHashSet(user))
                .deadline(LocalDate.of(2018,6, 10))
                .build();
        this.task8 = Task.builder()
                .name("FrontEnd")
                .description("stwornzie szkieletu fronu aplikacji")
                .taskStatus(TaskStatus.SPRINT_BACKLOG)
                .tag("[FE]")
                .taskPriority(TaskPriority.HIGH)
                .users(Sets.newHashSet(manager))
                .deadline(LocalDate.of(2018,6, 13))
                .build();
        this.task9 = Task.builder()
                .name("Security")
                .description("Zabezpieczenie aplikacji przed działaniami szkodzącymi")
                .taskStatus(TaskStatus.PRODUCT_BACKLOG)
                .tag("[SEC]")
                .taskPriority(TaskPriority.LOW)
                .users(Sets.newHashSet(user))
                .deadline(LocalDate.of(2018,6, 7))
                .build();
        this.task10 = Task.builder()
                .name("Wdrązenie")
                .description("Wdrązenie aplikacji na serwer")
                .taskStatus(TaskStatus.PRODUCT_BACKLOG)
                .tag("[END]")
                .taskPriority(TaskPriority.LOW)
                .users(Sets.newHashSet(user))
                .deadline(LocalDate.of(2018,6, 20))
                .build();

        this.task1.setProject(projectOne);
        this.task2.setProject(projectOne);
        this.task3.setProject(projectOne);
        this.task4.setProject(projectOne);
        this.task5.setProject(projectOne);
        this.task6.setProject(projectOne);
        this.task7.setProject(projectOne);
        this.task8.setProject(projectOne);
        this.task9.setProject(projectOne);
        this.task10.setProject(projectOne);

        this.projectOne.getTasks().add(task1);
        this.projectOne.getTasks().add(task2);
        this.projectOne.getTasks().add(task3);
        this.projectOne.getTasks().add(task4);
        this.projectOne.getTasks().add(task5);
        this.projectOne.getTasks().add(task6);
        this.projectOne.getTasks().add(task7);
        this.projectOne.getTasks().add(task8);
        this.projectOne.getTasks().add(task9);
        this.projectOne.getTasks().add(task10);

        this.user.getTasks().add(task1);
        this.user2.getTasks().add(task1);
        this.manager.getTasks().add(task2);
        this.user.getTasks().add(task3);
        this.user2.getTasks().add(task3);
        this.manager.getTasks().add(task4);
        this.user2.getTasks().add(task5);
        this.user.getTasks().add(task5);
        this.manager.getTasks().add(task6);
        this.user.getTasks().add(task7);
        this.user2.getTasks().add(task7);
        this.manager.getTasks().add(task8);
        this.user.getTasks().add(task9);
        this.user2.getTasks().add(task9);
        this.manager.getTasks().add(task10);

        this.task1 = taskRepository.save(task1);
        this.task2 = taskRepository.save(task2);
        this.task3 = taskRepository.save(task3);
        this.task4 = taskRepository.save(task4);
        this.task5 = taskRepository.save(task5);
        this.task6 = taskRepository.save(task6);
        this.task7 = taskRepository.save(task7);
        this.task8 = taskRepository.save(task8);
        this.task9 = taskRepository.save(task9);
        this.task10 = taskRepository.save(task10);

        this.projectOne = projectRepository.save(projectOne);

        this.user = userRepository.save(user);
        this.user2 = userRepository.save(user2);
        this.manager = userRepository.save(manager);

    }

    private void addMessages() {
        this.sentMessage = Message
                .builder()
                .sender(admin.getUsername())
                .receiver(user.getUsername())
                .title("Msg sent by admin to user")
                .contents("Message which will sent by admin to user")
                .sentDate(LocalDateTime.now())
                .users(new HashSet<>())
                .build();

        this.sentMessage2 = Message
                .builder()
                .sender(admin.getUsername())
                .receiver(user.getUsername())
                .title("Msg sent by admin to user")
                .contents("Message which will sent by admin to user")
                .sentDate(LocalDateTime.now())
                .users(new HashSet<>())
                .build();

        this.receivedMessage = Message
                .builder()
                .sender(user.getUsername())
                .receiver(admin.getUsername())
                .title("Msg sent by user to admin")
                .contents("Message which will sent by user to admin")
                .sentDate(LocalDateTime.now())
                .users(new HashSet<>())
                .build();

        this.receivedMessage2 = Message
                .builder()
                .sender(user.getUsername())
                .receiver(admin.getUsername())
                .title("Msg sent by user to admin")
                .contents("Message which will sent by user to admin")
                .sentDate(LocalDateTime.now())
                .users(new HashSet<>())
                .build();

        this.admin.getMessages().add(sentMessage);
        this.sentMessage.getUsers().add(admin);

        this.admin.getMessages().add(receivedMessage);
        this.receivedMessage.getUsers().add(admin);

        this.admin.getMessages().add(sentMessage2);
        this.sentMessage2.getUsers().add(admin);

        this.admin.getMessages().add(receivedMessage2);
        this.receivedMessage2.getUsers().add(admin);

        this.user.getMessages().add(sentMessage);
        this.sentMessage.getUsers().add(user);
        this.user.getMessages().add(receivedMessage);
        this.receivedMessage.getUsers().add(user);

        this.sentMessage = messageRepository.save(sentMessage);
        this.receivedMessage = messageRepository.save(receivedMessage);

        this.sentMessage2 = messageRepository.save(sentMessage2);
        this.receivedMessage2 = messageRepository.save(receivedMessage2);

        this.user = userRepository.save(user);
        this.admin = userRepository.save(admin);
    }
}