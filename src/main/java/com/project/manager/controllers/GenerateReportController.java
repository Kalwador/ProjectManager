package com.project.manager.controllers;

import com.project.manager.entities.Project;
import com.project.manager.services.ReportService;
import com.project.manager.services.SessionService;
import com.project.manager.ui.sceneManager.SceneManager;
import com.project.manager.ui.sceneManager.SceneType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This is the controller class which provide components of generate report window and gives them suitable actions
 */
@Component
@Getter
@Setter
public class GenerateReportController implements Initializable{

    private SceneManager sceneManager;
    private ReportService reportService;
    private SessionService sessionService;

    @Autowired
    public GenerateReportController(ReportService reportService) {
        this.sceneManager = SceneManager.getInstance();
        this.sessionService = SessionService.getInstance();
        this.reportService = reportService;
    }

    @FXML
    private Label title;
    @FXML
    private Label lastReportDate;
    @FXML
    private Label actualReportDate;
    @FXML
    private Label location;
    @FXML
    private RadioButton sentToMe;
    @FXML
    private RadioButton sentTo;
    @FXML
    private TextField email;
    @FXML
    private Button save;
    @FXML
    private Button generate;
    @FXML
    private Button cancel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setFields();
        disableEmailField();
        save.setOnAction(e -> setLocalization());

        cancel.setOnAction(e -> sceneManager.closeNewWindow(SceneType.GENERATE_REPORT));
        generate.setOnAction(e -> reportService.generateReport
                (sentToMe.isSelected(), email.getText(), this.location.getText()));
    }

    /**
     * This method set fields of dates in report window to get user knowledge about report periods
     */
    private void setFields() {
        Project project = sessionService.getProject();
        if (Optional.ofNullable(project).isPresent()) {
            title.setText(project.getProjectName());
            Optional.ofNullable(project.getLastRapportDate())
                    .ifPresent(localDate -> lastReportDate.setText(localDate.toString()));
            actualReportDate.setText(LocalDate.now().toString());
        }
    }

    /**
     * This method is apply to show localization which user set to save report file
     */
    private void setLocalization() {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showSaveDialog(null);
        if (Optional.ofNullable(file).isPresent()) {
            String path = file.getAbsolutePath();
            location.setText(path);
        }
    }

    /**
     * This method disable email field if sent to user radio button is unselected
     */
    private void disableEmailField() {
        sentTo.setOnAction(e -> {
            if (sentTo.isSelected()) email.setDisable(false);
            else email.setDisable(true);
        });
    }
}
