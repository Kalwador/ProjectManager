package com.project.manager.entities;

import com.project.manager.models.UserRole;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Proxy;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "USER_MODEL")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Proxy(lazy = false)
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @NotNull
    @NotEmpty
    @Min(value = 4)
    @Max(value = 25)
    private String username;

    @Email
    @NotNull
    @NotEmpty
    private String email;

    @NotNull
    @NotEmpty
    @Min(value = 8)
    @Max(value = 25)
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    /**
     * This properties can be change only by admin
     */
    private boolean isLocked;

    private boolean isBlocked;

    private String activationCode;

    @Min(0)
    @Max(3)
    @ColumnDefault("0")
    private int incorrectLoginCount;

    @Size(min = 1)
    private String firstName;

    @NotNull
    @Size(min = 1)
    private String lastName;

    @Lob
    @Column(name = "avatar")
    @Type(type = "org.hibernate.type.BinaryType")
    private byte[] avatar;

    @OneToMany(mappedBy = "manager", fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST})
    private Set<Project> projectsAsManager;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "USER_PROJECT",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "project_id")}
    )
    private Set<Project> projectsAsUser;

    @ManyToMany(fetch = FetchType.EAGER, targetEntity = Message.class)
    @JoinTable(
            name = "MESSAGE_USER",
            joinColumns = {@JoinColumn(name = "user_id", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "message_id", nullable = false)}
    )
    private Set<Message> messages;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "USER_TASK",
            joinColumns = {@JoinColumn(name = "user_id", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "task_id", nullable = false)}
    )
    private Set<Task> tasks;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserModel userModel = (UserModel) o;
        return isLocked == userModel.isLocked &&
                isBlocked == userModel.isBlocked &&
                incorrectLoginCount == userModel.incorrectLoginCount &&
                Objects.equals(id, userModel.id) &&
                Objects.equals(username, userModel.username) &&
                Objects.equals(email, userModel.email) &&
                Objects.equals(password, userModel.password) &&
                role == userModel.role &&
                Objects.equals(activationCode, userModel.activationCode) &&
                Objects.equals(firstName, userModel.firstName) &&
                Objects.equals(lastName, userModel.lastName) &&
                Arrays.equals(avatar, userModel.avatar) &&
                Objects.equals(projectsAsManager.size(), userModel.projectsAsManager.size()) &&
                Objects.equals(projectsAsUser.size(), userModel.projectsAsUser.size()) &&
                Objects.equals(messages.size(), userModel.messages.size()) &&
                Objects.equals(tasks.size(), userModel.tasks.size());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, username, email, password, role, isLocked, isBlocked, activationCode, incorrectLoginCount, firstName, lastName);
        result = 31 * result + Arrays.hashCode(avatar);
        return result;
    }

    @PreRemove
    private void preRemove() {
        projectsAsManager.forEach(project -> project.setManager(null));
        tasks.forEach(task -> task.getUsers().remove(this));
    }
}

