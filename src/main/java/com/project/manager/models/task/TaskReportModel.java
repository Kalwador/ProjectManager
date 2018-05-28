package com.project.manager.models.task;

import com.project.manager.entities.Task;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class TaskReportModel {

    private String taskName;
    private TaskPriority taskPriority;
    private TaskStatus taskStatus;
    private String tag;

    public static TaskReportModel convert(Task task) {
        return new TaskReportModel(task.getName(), task.getTaskPriority(),
                                   TaskStatus.values()[task.getTaskStatus()], task.getTag());
    }
}
