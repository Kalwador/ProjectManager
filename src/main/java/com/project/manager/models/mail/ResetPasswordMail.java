package com.project.manager.models.mail;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResetPasswordMail extends Mail {
    private String username;
    private String activationCode;
}