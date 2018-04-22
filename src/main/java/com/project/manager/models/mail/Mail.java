package com.project.manager.models.mail;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Mail {
    private String recipient;
    private MailSubject subject;
}