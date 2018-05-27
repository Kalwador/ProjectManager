package com.project.manager.controllers.message;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableView;
import com.project.manager.entities.Message;
import com.project.manager.exceptions.user.UserDoesNotExistException;
import com.project.manager.models.MessageTableView;
import com.project.manager.services.MessageService;
import com.project.manager.services.user.UserSelectorService;
import com.project.manager.ui.AlertManager;
import com.project.manager.ui.components.MessageWindowComponent;
import com.project.manager.ui.sceneManager.SceneManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import lombok.Getter;
import lombok.extern.log4j.Log4j;
import org.controlsfx.control.textfield.TextFields;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.project.manager.ui.AlertManager.showInformationAlert;

/**
 * That class is managing the components and views in message scene
 */
@Log4j
@Getter
@Component
public class MessageWindowController implements Initializable {

    /**
     * That fields are reference in order to {@link MessageWindowComponent} to provide reference to components
     * and manage them there, and last field is the reference to {@link SceneManager} which is responsible for
     * switching the scenes
     */
    private MessageWindowComponent messageWindowComponent;
    private MessageService messageService;
    private UserSelectorService userSelectorService;
    private SceneManager sceneManager;

    /**
     * That is construction of a class which inject {@link MessageWindowComponent} reference,
     * and set {@link SceneManager} reference
     * @param messageWindowComponent this is the parameter of {@link MessageWindowComponent} to inject
     */
    @Autowired
    public MessageWindowController(MessageWindowComponent messageWindowComponent, UserSelectorService userSelectorService,
                                   MessageService messageService) {
        this.messageWindowComponent = messageWindowComponent;
        this.userSelectorService = userSelectorService;
        this.messageService = messageService;
        this.sceneManager = SceneManager.getInstance();
    }

    /**
     * This fields are references to JavaFX components in message window
     */
    @FXML
    private Tab inbox;
    @FXML
    private Tab sentbox;
    @FXML
    private Tab sentMessageTab;
    @FXML
    private JFXTreeTableView<MessageTableView> inboxTable;
    @FXML
    private JFXTreeTableView<MessageTableView> sentboxTable;
    @FXML
    private JFXButton deleteMessages;
    @FXML
    private Button refresh;

    @FXML
    private Button sent;
    @FXML
    private Button clear;

    @FXML
    private TextField receiver;
    @FXML
    private TextField title;
    @FXML
    private TextArea content;
    private List<String> possibleUsers;

    /**
     * That methods is responsible for managing and rendering view in message window scene
     * @param location default framework parameter
     * @param resources default framework parameter
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadUsers();
        generateTables();
        showMessagesWindow();
        TextFields.bindAutoCompletion(receiver, possibleUsers);
        messageWindowComponent.setDeleteMessages(deleteMessages);
        messageWindowComponent.deleteSelectedMessages(inbox, sentbox, inboxTable, sentboxTable);

        refresh.setOnAction(e -> generateTables());
        sent.setOnAction(e -> sentMessage());
        clear.setOnAction(e -> clearMessage());
    }

    /**
     * That method use {@link MessageWindowComponent} to generate inbox and sentbox table
     */
    public void generateTables() {
        messageWindowComponent.generateInboxTableView(inboxTable);
        messageWindowComponent.generateSendBoxTableView(sentboxTable);
    }

    /**
     * That method is showing window which contains information about message by double click in message in table
     */
    private void showMessagesWindow() {
        inboxTable.setOnMousePressed(e -> {
            TreeItem<MessageTableView> item = inboxTable.getSelectionModel().getSelectedItem();
            if (e.isPrimaryButtonDown() && e.getClickCount() == 2 && Optional.ofNullable(item).isPresent()) {
                messageWindowComponent.showMessageWindow(item.getValue().getId().get());
            }
        });

        sentboxTable.setOnMousePressed(e -> {
            TreeItem<MessageTableView> item = sentboxTable.getSelectionModel().getSelectedItem();
            if (e.isPrimaryButtonDown() && e.getClickCount() == 2 && Optional.ofNullable(item).isPresent()) {
                messageWindowComponent.showMessageWindow(item.getValue().getId().get());
            }
        });
        sentMessageTab.setOnSelectionChanged(e -> {
            if (sentMessageTab.isSelected()) {
                deleteMessages.setVisible(false);
                refresh.setVisible(false);
            } else {
                deleteMessages.setVisible(true);
                refresh.setVisible(true);
            }
        });
    }

    /**
     * This method load user from database to list
     */
    private void loadUsers() {
        try {
            log.info("Loading possible users ...");
            possibleUsers = userSelectorService.getUserList();
            log.info("Loading possible users successful!");
        } catch (UserDoesNotExistException e) {
            log.warn("User try to load not existed account");
            AlertManager.showErrorAlert("User not exist!", "There was problem to load users, try to load windows again!");
        }
    }

    /**
     * That method prepare message object which will be sent
     * @return generated message with info from components
     */
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

    /**
     * That method sent message
     */
    private void sentMessage() {
        try {
            messageService.sentMessage(prepareMessage());
            refreshTables();
            clearMessage();
            sentbox.getTabPane().getSelectionModel().select(sentbox);
        } catch (UserDoesNotExistException e) {
            log.warn("User with username : '" + receiver.getText() + "' does not exist!");
            showInformationAlert("User does not found", "There is no user of that username in database");
        }
    }

    /**
     * That method is responsible for refreshing messages tables
     */
    private void refreshTables() {
        generateTables();
    }

    /**
     * That method generate confirmation alert to get users possibility to decide to cancel writing the message
     */
    private void clearMessage() {
        receiver.clear();
        title.clear();
        content.clear();
    }
}
