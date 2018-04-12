package com.project.manager.models;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.project.manager.entities.Project;
import com.project.manager.ui.GraphicButtonGenerator;
import javafx.beans.property.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * This is the class created to comfortable make object to display information about project in admin project view
 */
@Setter
@Getter
@Builder
public class ProjectTableView extends RecursiveTreeObject<ProjectTableView> {

    private LongProperty id;

    private StringProperty projectName;

    private StringProperty managerFirstAndLastName;

    private StringProperty clientFirstAndLastName;

    private IntegerProperty countOfMembers;

    private SimpleObjectProperty<JFXButton> delete;

    private SimpleObjectProperty<JFXCheckBox> check;

    /**
     * This is the method which convert original Project entity from database to our class for making pretty view class
     * to displaying information about project for admin
     *
     * @param project This parameter is original project for converting to Project view class
     * @return method return already converted original project to project view class
     */
    public static ProjectTableView convert(Project project) {
        return ProjectTableView.builder()
                .id(new SimpleLongProperty(project.getId()))
                .projectName(new SimpleStringProperty(project.getProjectName()))
                .managerFirstAndLastName(new SimpleStringProperty(project.getManager().getFirstName() + " " + project.getManager().getLastName()))
                .countOfMembers(new SimpleIntegerProperty(project.getMembers().size()))
                .check(new SimpleObjectProperty<>(new JFXCheckBox()))
                .build();
    }

    /**
     * This is the method to generate delete button in table
     *
     * @param projectTableView this is the projectView object for modify it to contain this button inside
     * @return method will return ProjectView object with delete button inside
     */
    public ProjectTableView generateDelButton(ProjectTableView projectTableView) {
        JFXButton del = new GraphicButtonGenerator().getJfxButtonWithGraphic("/images/delete.png");
        projectTableView.setDelete(new SimpleObjectProperty<>(del));
        return projectTableView;
    }
}
