package com.project.manager.models.mail;

import lombok.*;

import java.io.File;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectReportMail extends Mail {
    private String recipientName;
    private String projectName;
    private String startDate;
    private String endDate;
    private File attachment;
}
