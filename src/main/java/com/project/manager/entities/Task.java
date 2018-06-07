package com.project.manager.entities;

import com.project.manager.models.task.TaskPriority;
import com.project.manager.models.task.TaskStatus;
import lombok.*;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "TASK")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Proxy(lazy = false)
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    private String description;

    @Enumerated
    private TaskStatus taskStatus;

    private String tag;

    @Enumerated
    private TaskPriority taskPriority;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToMany(mappedBy = "tasks", fetch = FetchType.EAGER, targetEntity = UserModel.class)
    private Set<UserModel> users;

    @NotNull
    private LocalDate deadline;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return taskStatus == task.taskStatus &&
                Objects.equals(id, task.id) &&
                Objects.equals(name, task.name) &&
                Objects.equals(description, task.description) &&
                Objects.equals(tag, task.tag) &&
                Objects.equals(taskPriority, task.taskPriority) &&
                Objects.equals(project, task.project) &&
                Objects.equals(users.size(), task.users.size()) &&
                Objects.equals(deadline, task.deadline);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, taskStatus, tag, taskPriority, project, deadline);
    }

    @PreRemove
    private void preRemove() {
        users.forEach(userModel -> userModel.getTasks().remove(this));
    }
}
