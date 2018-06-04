package com.project.manager.services;

import com.project.manager.models.task.TaskStatus;
import com.project.manager.ui.components.TaskGenerator;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Class provides methoda used in generate tasks in specified Vboxes for user or manager & admin
 */
@Service
public class TaskGeneratorService {
    private TaskGenerator taskGenerator;

    @Autowired
    public TaskGeneratorService(TaskGenerator taskGenerator) {
        this.taskGenerator = taskGenerator;
    }

    /**
     * Inject tasks of chosen project to specified VBoxes
     */
    public void injectTasksToVBoxes(VBox productBacklogVBox, VBox sprintBacklogVBox,VBox inProgressVBox,
                                     VBox testingVBox,VBox codeReviewVBox,VBox doneVBox) {
        taskGenerator.inject(productBacklogVBox, TaskStatus.PRODUCT_BACKLOG);
        taskGenerator.inject(sprintBacklogVBox, TaskStatus.SPRINT_BACKLOG);
        taskGenerator.inject(inProgressVBox, TaskStatus.IN_PROGRESS);
        taskGenerator.inject(testingVBox, TaskStatus.TESTING);
        taskGenerator.inject(codeReviewVBox, TaskStatus.CODE_REVIEW);
        taskGenerator.inject(doneVBox, TaskStatus.DONE);
    }
    /**
     * Inject tasks of chosen project to specified VBoxes for only one user
     */
    public void injectTasksToVBoxesForUser(VBox productBacklogVBox, VBox sprintBacklogVBox,VBox inProgressVBox,
                                            VBox testingVBox,VBox codeReviewVBox,VBox doneVBox, Long userId) {
        taskGenerator.injectOnlyForOneUser(productBacklogVBox, TaskStatus.PRODUCT_BACKLOG, userId);
        taskGenerator.injectOnlyForOneUser(sprintBacklogVBox, TaskStatus.SPRINT_BACKLOG, userId);
        taskGenerator.injectOnlyForOneUser(inProgressVBox, TaskStatus.IN_PROGRESS, userId);
        taskGenerator.injectOnlyForOneUser(testingVBox, TaskStatus.TESTING, userId);
        taskGenerator.injectOnlyForOneUser(codeReviewVBox, TaskStatus.CODE_REVIEW, userId);
        taskGenerator.injectOnlyForOneUser(doneVBox, TaskStatus.DONE, userId);
    }
}
