package com.project.manager.entities;

import com.project.manager.models.UserRole;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Proxy;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import javax.validation.constraints.*;
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
    @Min(value = 4)@Max(value = 25)
    private String username;

    @Email
    @NotNull
    @NotEmpty
    private String email;

    @NotNull
    @NotEmpty
    @Min(value = 8)@Max(value = 25)
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

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private Set<Task> tasks;

    @PreRemove
    private void preRemove() {
        projectsAsManager.forEach(project -> project.setManager(null));
        this.getTasks().forEach(task -> task.setUser(null));
    }
}

