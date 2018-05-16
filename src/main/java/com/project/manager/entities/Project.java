package com.project.manager.entities;

import lombok.*;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "PROJECT")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Proxy(lazy = false)
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private Long id;

    @NotNull
    @NotEmpty
    private String projectName;

    @NotNull
    @NotEmpty
    private String projectInformation;

    @ManyToOne
    @JoinColumn(name = "MANAGER_ID")
    private UserModel manager;

    @Column(name = "LAST_RAPPORT_DATE")
    private LocalDate lastRapportDate;

    @ManyToMany(mappedBy = "projectsAsUser", fetch = FetchType.EAGER)
    private Set<UserModel> members;

    @OneToMany(mappedBy = "project", fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<Task> tasks;

    @PreRemove
    private void preRemove() {
        members.forEach(userModel -> userModel.getProjectsAsUser().remove(this));
        tasks.forEach(task -> task.setProject(null));
        manager.getProjectsAsManager().remove(this);
    }
}