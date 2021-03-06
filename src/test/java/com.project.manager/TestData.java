package com.project.manager;

import com.project.manager.entities.Project;
import com.project.manager.entities.Task;
import com.project.manager.entities.UserModel;
import com.project.manager.models.UserRole;
import com.project.manager.models.task.TaskPriority;
import com.project.manager.models.task.TaskStatus;
import com.project.manager.utils.BCryptEncoder;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

public class TestData {
    public static UserModel getUser() {
        UserModel user = UserModel.builder()
                .username("user")
                .activationCode(String.valueOf(new Date().getTime()))
                .password(BCryptEncoder.encode("PASSWORD"))
                .email("user@mail.com")
                .role(UserRole.USER)
                .isLocked(false)
                .projectsAsManager(new HashSet<>())
                .build();
        return user;
    }

    public static UserModel getAdmin() {
        UserModel admin = UserModel.builder()
                .username("admin")
                .activationCode(String.valueOf(new Date().getTime()))
                .password(BCryptEncoder.encode("PASSWORD"))
                .email("admin@mail.com")
                .role(UserRole.ADMIN)
                .isLocked(false)
                .projectsAsManager(new HashSet<>())
                .build();
        return admin;
    }

    public static Project getProject() {
        Project project = Project.builder()
                .projectName("Lotnisko")
                .projectInformation("project1Info")
                .members(new HashSet<>())
                .tasks(new HashSet<>(getTasks()))
                .build();
        return project;
    }

    public static List<Task> getTasks(){
        Task task1 = Task.builder()
                .name("Stworzenie szkieletu")
                .description("Podstawowy szkielet funkcionalnej aplikacji")
                .taskStatus(TaskStatus.DONE)
                .tag("START")
                .taskPriority(TaskPriority.HIGH)
                .build();
        Task task2 = Task.builder()
                .name("Baza danych")
                .description("Dodanie do projektu połaczenia z baza danych")
                .taskStatus(TaskStatus.DONE)
                .tag("BD")
                .taskPriority(TaskPriority.HIGH)
                .build();
        Task task3 = Task.builder()
                .name("Entity")
                .description("Stworznie wszystkich wymaganych entity na potrzeby projektu")
                .taskStatus(TaskStatus.CODE_REVIEW)
                .tag("BD")
                .taskPriority(TaskPriority.MEDIUM)
                .build();
        Task task4 = Task.builder()
                .name("Połaczenie z bazą")
                .description("Stworznie repozytowrów JPA dla entity oraz wykonanie testów zapisu do bazy dancyh")
                .taskStatus(TaskStatus.TESTING)
                .tag("JP")
                .taskPriority(TaskPriority.MEDIUM)
                .build();
        Task task5 = Task.builder()
                .name("Logika aplikacji")
                .description("stworznie potrzebnej logiki aplikacji do zarazdzania i kontrolą lotów na lotnisku")
                .taskStatus(TaskStatus.TESTING)
                .tag("SERVICE")
                .taskPriority(TaskPriority.HIGH)
                .build();
        Task task6 = Task.builder()
                .name("kontrolery")
                .description("Dodanie potrzebnych kontrolerów")
                .taskStatus(TaskStatus.IN_PROGRESS)
                .tag("REST")
                .taskPriority(TaskPriority.MEDIUM)
                .build();
        Task task7 = Task.builder()
                .name("testownie")
                .description("Testownie integracyjne bazy andych z logiką oraz kontrolerami")
                .taskStatus(TaskStatus.SPRINT_BACKLOG)
                .tag("[TEST]")
                .taskPriority(TaskPriority.LOW)
                .build();
        Task task8 = Task.builder()
                .name("FrontEnd")
                .description("stwornzie szkieletu fronu aplikacji")
                .taskStatus(TaskStatus.SPRINT_BACKLOG)
                .tag("[FE]")
                .taskPriority(TaskPriority.HIGH)
                .build();
        Task task9 = Task.builder()
                .name("Security")
                .description("Zabezpieczenie aplikacji przed działaniami szkodzącymi")
                .taskStatus(TaskStatus.PRODUCT_BACKLOG)
                .tag("[SEC]")
                .taskPriority(TaskPriority.LOW)
                .build();
        Task task10 = Task.builder()
                .name("Wdrązenie")
                .description("Wdrązenie aplikacji na serwer")
                .taskStatus(TaskStatus.PRODUCT_BACKLOG)
                .tag("[END]")
                .taskPriority(TaskPriority.LOW)
                .build();
        return Arrays.asList(task1,task2,task3,task4,task5,task6,task7,task8,task9,task10);
    }
}
