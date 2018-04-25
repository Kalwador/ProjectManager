package com.project.manager.controllers;

import com.project.manager.entities.Message;
import com.project.manager.exceptions.user.UserDoesNotExistException;
import com.project.manager.services.MessageService;
import com.project.manager.services.user.UserSelectorService;
import com.project.manager.ui.components.admin.AdminDashboardTablesComponent;
import com.project.manager.ui.sceneManager.SceneManager;
import com.project.manager.ui.sceneManager.SceneType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.controlsfx.control.textfield.TextFields;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;

import static com.project.manager.ui.AlertManager.showConfirmationAlert;
import static com.project.manager.ui.AlertManager.showInformationAlert;

@Component
public class SentMessageController implements Initializable {

    private MessageService messageService;
    private UserSelectorService userSelectorService;
    private SceneManager sceneManager;
    private AdminDashboardTablesComponent adminDashboardTablesComponent;

    @Autowired
    public SentMessageController(MessageService messageService, UserSelectorService userSelectorService,
                                 AdminDashboardTablesComponent adminDashboardTablesComponent) {
        this.messageService = messageService;
        this.adminDashboardTablesComponent = adminDashboardTablesComponent;
        this.userSelectorService = userSelectorService;
        this.sceneManager = SceneManager.getInstance();
    }

    @FXML
    private Button sent;
    @FXML
    private Button cancel;

    @FXML
    private TextField receiver;
    @FXML
    private TextField title;
    @FXML
    private TextArea content;
    private List<String> possibleUsers;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        possibleUsers = userSelectorService.getUserList();
        TextFields.bindAutoCompletion(receiver, possibleUsers);

        sent.setOnAction(e -> sentMessage());
        cancel.setOnAction(e -> cancelMessageAlert());
    }

    private void cancelMessageAlert() {
        Alert alert = showConfirmationAlert
                ("Cancel message", "Are you sure you want to cancel writing the message");
        if (alert.getResult().equals(ButtonType.OK)) {
            sceneManager.closeNewWindow(SceneType.MESSAGE_SENT_WINDOW.ordinal());
        }
    }

    private Message prepareMessage() {
        return Message
                .builder()
                .sentDate(LocalDateTime.now())
                .title(title.getText())
                .receiver(receiver.getText())
                .contents(content.getText())
                .users(new HashSet<>())
                .build();
    }

    private void sentMessage() {
        try {
            messageService.sentMessage(prepareMessage());
            adminDashboardTablesComponent.generateInboxAndSentTableView();
            sceneManager.closeNewWindow(SceneType.MESSAGE_SENT_WINDOW);

        } catch (UserDoesNotExistException e) {
            showInformationAlert("User does not found", "The is no user of that email in service");
        }
    }
}
