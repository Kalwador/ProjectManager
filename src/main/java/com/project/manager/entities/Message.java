package com.project.manager.entities;


import lombok.*;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "MESSAGE")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Proxy(lazy = false)
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotEmpty
    private String sender;

    @NotNull
    @NotEmpty
    private String receiver;

    @NotNull
    @NotEmpty
    private String title;

    @NotNull
    @NotEmpty
    private String contents;

    private LocalDateTime sentDate;

    @ManyToMany
    @JoinTable(
            name = "MESSAGE_USER",
            joinColumns = {@JoinColumn(name = "message_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    private Set<UserModel> users;
}
