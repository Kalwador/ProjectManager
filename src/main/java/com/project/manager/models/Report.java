package com.project.manager.models;

import com.project.manager.models.task.TaskReportModel;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

/**
 * This class is representation of Report, create to support generate report of project in pdf
 */
@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Report {

    private String title;
    private LocalDate lastReportDate;
    private LocalDate actualDate;
    private List<String> team;
    private List<TaskReportModel> newTasks;
    private List<TaskReportModel> currentTasks;
    private List<TaskReportModel> doneTasks;
}
