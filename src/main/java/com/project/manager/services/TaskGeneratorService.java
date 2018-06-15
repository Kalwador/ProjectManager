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

    private VBox productBacklogVBox;
    private VBox sprintBacklogVBox;
    private VBox inProgressVBox;
    private VBox testingVBox;
    private VBox codeReviewVBox;
    private VBox doneVBox;

    @Autowired
    public TaskGeneratorService(TaskGenerator taskGenerator) {
        this.taskGenerator = taskGenerator;
    }

    /**
     * This method is setting up references to the VBox when we want to inject data to them
     * @param productBacklogVBox
     * @param sprintBacklogVBox
     * @param inProgressVBox
     * @param testingVBox
     * @param codeReviewVBox
     * @param doneVBox
     */
    public void setUpReferences(VBox productBacklogVBox, VBox sprintBacklogVBox,VBox inProgressVBox,
                                VBox testingVBox,VBox codeReviewVBox,VBox doneVBox) {
        this.productBacklogVBox = productBacklogVBox;
        this.sprintBacklogVBox = sprintBacklogVBox;
        this.inProgressVBox = inProgressVBox;
        this.testingVBox = testingVBox;
        this.codeReviewVBox = codeReviewVBox;
        this.doneVBox = doneVBox;
    }

    /**
     * Inject tasks of chosen project to specified VBoxes
     */
    public void injectTasksToVBoxes() {
        clearVBoxes();
        taskGenerator.inject(productBacklogVBox, TaskStatus.PRODUCT_BACKLOG);
        taskGenerator.inject(sprintBacklogVBox, TaskStatus.SPRINT_BACKLOG);
        taskGenerator.inject(inProgressVBox, TaskStatus.IN_PROGRESS);
        taskGenerator.inject(testingVBox, TaskStatus.TESTING);
        taskGenerator.inject(codeReviewVBox, TaskStatus.CODE_REVIEW);
        taskGenerator.inject(doneVBox, TaskStatus.DONE);
    }

    /**
     * Method to clear boxes
     */
    private void clearVBoxes() {
        productBacklogVBox.getChildren().clear();
        sprintBacklogVBox.getChildren().clear();
        inProgressVBox.getChildren().clear();
        testingVBox.getChildren().clear();
        codeReviewVBox.getChildren().clear();
        doneVBox.getChildren().clear();
    }

    /**
     * Inject tasks of chosen project to specified VBoxes for only one user
     */
    public void injectTasksToVBoxesForUser(Long userId) {
        taskGenerator.injectOnlyForOneUser(productBacklogVBox, TaskStatus.PRODUCT_BACKLOG, userId);
        taskGenerator.injectOnlyForOneUser(sprintBacklogVBox, TaskStatus.SPRINT_BACKLOG, userId);
        taskGenerator.injectOnlyForOneUser(inProgressVBox, TaskStatus.IN_PROGRESS, userId);
        taskGenerator.injectOnlyForOneUser(testingVBox, TaskStatus.TESTING, userId);
        taskGenerator.injectOnlyForOneUser(codeReviewVBox, TaskStatus.CODE_REVIEW, userId);
        taskGenerator.injectOnlyForOneUser(doneVBox, TaskStatus.DONE, userId);
    }
}
