package com.project.manager.models;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonalData {
    private String firstName;
    private String lastName;
    private String password;
    private byte[] avatar;
}
