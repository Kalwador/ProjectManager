package com.project.manager.entities;

import lombok.*;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
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
    @Column(name = "message_id")
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

    @ManyToMany(mappedBy = "messages", fetch = FetchType.EAGER, targetEntity = UserModel.class)
    private Set<UserModel> users;

    @PreRemove
    private void preRemove() {
        users.forEach(userModel -> userModel.getMessages().remove(this));
    }
}