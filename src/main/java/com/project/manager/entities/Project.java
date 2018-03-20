package com.project.manager.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "PROJECT")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotEmpty
    private String projectName;

    @NotNull
    @NotEmpty
    private String projectInformation;

    @ManyToOne
    @JoinColumn(name = "MANAGER_ID")
    @NotNull
    private UserModel manager;

    @ManyToMany(mappedBy = "projectsAsClient", fetch = FetchType.EAGER)//, fetch = FetchType.EAGER)
    private Set<UserModel> clients;

    @ManyToMany(mappedBy = "projectsAsUser", fetch = FetchType.EAGER)//, fetch = FetchType.EAGER)
    private Set<UserModel> members;
}
