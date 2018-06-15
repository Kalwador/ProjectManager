package com.project.manager.admin.projects;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableView;
import com.project.manager.JavaFXThreadingRule;
import com.project.manager.controllers.admin.AdminDashboardController;
import com.project.manager.entities.Message;
import com.project.manager.entities.Project;
import com.project.manager.entities.UserModel;
import com.project.manager.models.ProjectTableView;
import com.project.manager.models.UserRole;
import com.project.manager.models.UserTableView;
import com.project.manager.services.MessageService;
import com.project.manager.services.ProjectService;
import com.project.manager.services.user.UserService;
import com.project.manager.ui.components.admin.AdminDashboardTablesComponent;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.util.collections.Sets;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AdminDashboardTablesComponentTest {

    @Rule
    public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();

    @Mock
    private ProjectService projectService;

    @Mock
    private UserService userService;

    @Mock
    private MessageService messageService;

    @Mock
    private AdminDashboardController adminDashboardController;

    @InjectMocks
    private AdminDashboardTablesComponent adminDashboardTablesComponent;


    @Test
    public void generateProjectTableViewTest() {
        JFXTreeTableView<ProjectTableView> table = new JFXTreeTableView<>();
        when(adminDashboardController.getProjectTable()).thenReturn(table);
        when(projectService.getAllProjects()).thenReturn(getExampleProjects());

        adminDashboardTablesComponent.generateProjectTableView();


        assertNotNull(AdminDashboardTablesComponent.projectTableViews);
        assertEquals(AdminDashboardTablesComponent.projectTableViews.size(), 2);

        assertEquals(AdminDashboardTablesComponent.projectTableViews.get(0).getId().getValue(),
                getExampleProjects().get(0).getId());
        assertEquals(AdminDashboardTablesComponent.projectTableViews.get(0).getProjectName().getValue(),
                getExampleProjects().get(0).getProjectName());
        assertEquals(AdminDashboardTablesComponent.projectTableViews.get(0).getManagerFirstAndLastName().getValue(),
                getExampleProjects().get(0).getManager().getFirstName() + " "
                        + getExampleProjects().get(0).getManager().getLastName());
        assertEquals(AdminDashboardTablesComponent.projectTableViews.get(0).getCountOfMembers().get(),
                getExampleProjects().get(0).getMembers().size());


        assertEquals(table.getColumns().size(), 7);
        assertNotNull(table.getColumns().get(0).getCellObservableValue(0).getValue());
        assertEquals(table.getColumns().get(1).getCellObservableValue(0).getValue(), 1L);
        assertEquals(table.getColumns().get(2).getCellObservableValue(0).getValue(), "projectOne");
        assertEquals(table.getColumns().get(3).getCellObservableValue(0).getValue(), "Adam Manager");
        assertEquals(table.getColumns().get(5).getCellObservableValue(0).getValue(), 1);
    }

    @Test
    public void generateUsersTableViewTest() {
        List<UserModel> users = getExampleUsers();
        JFXTreeTableView<UserTableView> table = new JFXTreeTableView<>();
        when(adminDashboardController.getUserTable()).thenReturn(table);
        when(userService.getAllUsers()).thenReturn(users);

        adminDashboardTablesComponent.generateUserTableView();


        assertNotNull(AdminDashboardTablesComponent.userTableViews);
        assertEquals(AdminDashboardTablesComponent.userTableViews.size(), 3);
        assertEquals(AdminDashboardTablesComponent.userTableViews.get(0).getId().getValue(),
                users.get(0).getId());
        assertEquals(AdminDashboardTablesComponent.userTableViews.get(0).getIsLocked().asObject().get(),
                users.get(0).isLocked());
        assertEquals(AdminDashboardTablesComponent.userTableViews.get(0).getEmail().get(),
                users.get(0).getEmail());
        assertEquals(AdminDashboardTablesComponent.userTableViews.get(0).getRole().get(),
                users.get(0).getRole().name());
        assertEquals(AdminDashboardTablesComponent.userTableViews.get(0).getUsername().get(),
                users.get(0).getUsername());


        assertEquals(table.getColumns().size(), 12);

        assertNotNull(table.getColumns().get(0).getCellObservableValue(0).getValue());
        assertEquals(table.getColumns().get(1).getCellObservableValue(0).getValue(),
                users.get(0).getId());
        assertEquals(table.getColumns().get(2).getCellObservableValue(0).getValue(),
                users.get(0).getUsername());
        assertEquals(table.getColumns().get(3).getCellObservableValue(0).getValue(),
                users.get(0).getEmail());
        assertEquals(table.getColumns().get(4).getCellObservableValue(0).getValue(),
                users.get(0).getRole().toString());
        assertEquals(table.getColumns().get(7).getCellObservableValue(0).getValue().getClass(),
                JFXButton.class);
        assertEquals(table.getColumns().get(8).getCellObservableValue(0).getValue().getClass(),
                JFXButton.class);
        assertEquals(table.getColumns().get(9).getCellObservableValue(0).getValue().getClass(),
                JFXButton.class);

    }

//    @Test
//    public void generateMessageInboxTableViewTest() {
//        List<Message> messages = getExampleMessage();
//        JFXTreeTableView<MessageTableView> inboxTable = new JFXTreeTableView<>();
//        JFXTreeTableView<MessageTableView> sentboxTable = new JFXTreeTableView<>();
//        when(adminDashboardController.getInboxTable()).thenReturn(inboxTable);
//        when(adminDashboardController.getSentboxTable()).thenReturn(sentboxTable);
//
//        when(messageService.getAllReceivedMessages()).thenReturn(messages);
//        when(messageService.getAllSentMessages()).thenReturn(messages);
//
//        adminDashboardTablesComponent.generateInboxAndSentTableView();
//
//
//        assertNotNull(AdminDashboardTablesComponent.receivedMessages);
//        assertNotNull(AdminDashboardTablesComponent.sentMessages);
//
//        assertEquals(AdminDashboardTablesComponent.receivedMessages.size(), 2);
//        assertEquals(AdminDashboardTablesComponent.sentMessages.size(), 2);
//
//        assertEquals(AdminDashboardTablesComponent.receivedMessages.get(0).getId().getValue(),
//                messages.get(0).getId());
//        assertEquals(AdminDashboardTablesComponent.receivedMessages.get(0).getReceiver().get(),
//                messages.get(0).getReceiver());
//        assertEquals(AdminDashboardTablesComponent.receivedMessages.get(0).getSender().get(),
//                messages.get(0).getSender());
//        assertEquals(AdminDashboardTablesComponent.receivedMessages.get(0).getTitle().get(),
//                messages.get(0).getTitle());
//        assertEquals(AdminDashboardTablesComponent.receivedMessages.get(0).getSentDate().get(),
//                messages.get(0).getSentDate().format(DateTimeFormatter.ISO_DATE));
//        assertEquals(AdminDashboardTablesComponent.receivedMessages.get(0).getContents().get(),
//                messages.get(0).getContents());
//
//        assertEquals(AdminDashboardTablesComponent.sentMessages.get(0).getId().getValue(),
//                messages.get(0).getId());
//        assertEquals(AdminDashboardTablesComponent.sentMessages.get(0).getReceiver().get(),
//                messages.get(0).getReceiver());
//        assertEquals(AdminDashboardTablesComponent.sentMessages.get(0).getSender().get(),
//                messages.get(0).getSender());
//        assertEquals(AdminDashboardTablesComponent.sentMessages.get(0).getTitle().get(),
//                messages.get(0).getTitle());
//        assertEquals(AdminDashboardTablesComponent.sentMessages.get(0).getSentDate().get(),
//                messages.get(0).getSentDate().format(DateTimeFormatter.ISO_DATE));
//        assertEquals(AdminDashboardTablesComponent.sentMessages.get(0).getContents().get(),
//                messages.get(0).getContents());
//
//
//        assertEquals(inboxTable.getColumns().size(), 3);
//        assertEquals(sentboxTable.getColumns().size(), 3);
//
//        assertEquals(inboxTable.getColumns().get(0).getCellObservableValue(0).getValue(),
//                messages.get(0).getSender());
//        assertEquals(inboxTable.getColumns().get(1).getCellObservableValue(0).getValue(),
//                messages.get(0).getTitle());
//        assertEquals(inboxTable.getColumns().get(2).getCellObservableValue(0).getValue(),
//                messages.get(0).getSentDate().format(DateTimeFormatter.ISO_DATE));
//
//        assertEquals(sentboxTable.getColumns().get(0).getCellObservableValue(0).getValue(),
//                messages.get(0).getReceiver());
//        assertEquals(sentboxTable.getColumns().get(1).getCellObservableValue(0).getValue(),
//                messages.get(0).getTitle());
//        assertEquals(sentboxTable.getColumns().get(2).getCellObservableValue(0).getValue(),
//                messages.get(0).getSentDate().format(DateTimeFormatter.ISO_DATE));
//
//    }


    public List<Project> getExampleProjects() {
        Set<UserModel> memberList = Sets.newSet(getExampleUsers().get(1));
        UserModel manager = getExampleUsers().get(0);
        UserModel client = getExampleUsers().get(2);

        Project projectOne = Project.builder()
                .id(1L)
                .projectName("projectOne")
                .projectInformation("project one inf")
                .manager(getExampleUsers().get(0))
                .members(memberList)
                .build();

        Project projectTwo = Project.builder()
                .id(2L)
                .projectName("projectTwo")
                .projectInformation("project two inf")
                .manager(manager)
                .members(memberList)
                .build();

        return Arrays.asList(projectOne, projectTwo);
    }

    public List<UserModel> getExampleUsers() {
        return Arrays.asList(
                UserModel.builder()
                        .id(1l)
                        .firstName("Adam")
                        .lastName("Manager")
                        .username("adam")
                        .email("adam@mail.com")
                        .projectsAsManager(new HashSet<>())
                        .projectsAsUser(new HashSet<>())
                        .role(UserRole.USER)
                        .build(),
                UserModel.builder()
                        .id(2l)
                        .firstName("Adam")
                        .lastName("Member")
                        .username("adam")
                        .email("adam@mail.com")
                        .projectsAsManager(new HashSet<>())
                        .projectsAsUser(new HashSet<>())
                        .role(UserRole.USER)
                        .build(),
                UserModel.builder()
                        .id(3l)
                        .firstName("Adam")
                        .lastName("Client")
                        .username("adam")
                        .email("adam@mail.com")
                        .projectsAsManager(new HashSet<>())
                        .projectsAsUser(new HashSet<>())
                        .role(UserRole.USER)
                        .build()
        );
    }

    public List<Message> getExampleMessage() {
        return Arrays.asList(
                Message
                        .builder()
                        .id(1L)
                        .receiver("receiver@mail.com")
                        .sender("sender@mail.com")
                        .title("title")
                        .contents("contents")
                        .users(new HashSet<>())
                        .sentDate(LocalDateTime.now())
                        .build(),
                Message
                        .builder()
                        .id(2L)
                        .receiver("receiver2@mail.com")
                        .sender("sender2@mail.com")
                        .title("title2")
                        .contents("contents2")
                        .users(new HashSet<>())
                        .sentDate(LocalDateTime.now())
                        .build()
        );
    }
}
