package com.project.manager.ui.components;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.project.manager.entities.Message;
import com.project.manager.exceptions.message.MessageNotExistException;
import com.project.manager.models.MessageTableView;
import com.project.manager.services.MessageService;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * This class is responsible for generating message views in tables, and provides some functionality assign to some buttons
 */
@Component
public class MessageWindowComponent {

    /**
     * List off all received and sent messages by actual logged account
     */
    private ObservableList<MessageTableView> receivedMessages;
    private ObservableList<MessageTableView> sentMessages;
    /**
     * Lists helps to store information about which messages was selected
     */
    private List<Long> selectedInboxMessagesIds;
    private List<Long> selectedSentboxMessagesIds;

    private JFXButton deleteMessages;

    private MessageService messageService;


    /**
     * Constructor of class, declaring reference to message service
     * @param messageService this is injected message service
     */
    @Autowired
    public MessageWindowComponent(MessageService messageService) {
        this.messageService = messageService;
    }

    /**
     * This method generate table with all received messages
     * @param inboxTable is the reference to table
     */
    public void generateInboxTableView(JFXTreeTableView<MessageTableView> inboxTable) {
        inboxTable.getColumns().clear();

        List<Message> received = messageService.getAllReceivedMessages();
        if (!received.isEmpty()) {
            receivedMessages = FXCollections.observableList(received.stream()
                    .map(MessageTableView::convert)
                    .map(messageTableView -> messageTableView.generateDelButton(messageTableView))
                    .peek(u -> u.getDelete().getValue()
                            .setOnAction(e -> {
                                deleteMessage(u);
                                generateInboxTableView(inboxTable);
                            }))
                    .peek(messageTableView -> messageTableView.getCheck().get().setOnAction(e -> disableInboxDeleteButton()))
                    .collect(Collectors.toList()));

            inboxTable.setOnMouseClicked(e -> {
                int focusedIndex = inboxTable.getSelectionModel().getFocusedIndex();
                if (focusedIndex >= 0) {
                    receivedMessages.get(focusedIndex).getCheck().get().fire();
                    inboxTable.getSelectionModel().clearSelection();
                }
            });

            TreeTableColumn<MessageTableView, CheckBox> checkColumn = new TreeTableColumn<>("");
            TreeTableColumn<MessageTableView, String> receiverColumn = new TreeTableColumn<>("From");
            TreeTableColumn<MessageTableView, String> receivedTitleColumn = new TreeTableColumn<>("Title");
            TreeTableColumn<MessageTableView, String> receivedDateColumn = new TreeTableColumn<>("Date");
            TreeTableColumn<MessageTableView, JFXButton> deleteButtonColumn = new TreeTableColumn<>("");

            checkColumn.setSortable(false);
            receiverColumn.setSortable(false);
            receivedDateColumn.setSortable(false);
            receivedDateColumn.setSortable(false);
            deleteButtonColumn.setSortable(false);

            inboxTable.getColumns().addAll(checkColumn, receiverColumn, receivedTitleColumn, receivedDateColumn, deleteButtonColumn);

            checkColumn.setCellValueFactory(u -> new SimpleObjectProperty(u.getValue().getValue().getCheck().get()));
            receiverColumn.setCellValueFactory(m -> m.getValue().getValue().getSender());
            receivedTitleColumn.setCellValueFactory(m -> m.getValue().getValue().getTitle());
            receivedDateColumn.setCellValueFactory(m -> m.getValue().getValue().getSentDate());
            deleteButtonColumn.setCellValueFactory(u -> new SimpleObjectProperty(u.getValue().getValue().getDelete().get()));

            TreeItem<MessageTableView> inboxItem = new RecursiveTreeItem<MessageTableView>(receivedMessages, RecursiveTreeObject::getChildren);

            inboxTable.setRoot(inboxItem);
            inboxTable.setShowRoot(false);
        }



    }

    /**
     * This method generate tables including all sent messages
     * @param sentboxTable is the reference to table
     */
    public void generateSendBoxTableView(JFXTreeTableView<MessageTableView> sentboxTable) {
        sentboxTable.getColumns().clear();
        List<Message> sent = messageService.getAllSentMessages();
        if (!sent.isEmpty()) {
            sentMessages = FXCollections.observableList(sent.stream()
                    .map(MessageTableView::convert)
                    .map(messageTableView -> messageTableView.generateDelButton(messageTableView))
                    .peek(u -> u.getDelete().getValue()
                            .setOnAction(e -> {
                                deleteMessage(u);
                                generateSendBoxTableView(sentboxTable);
                            }))
                    .peek(messageTableView -> messageTableView.getCheck().get().setOnAction(e -> disableSentboxDeleteButton()))
                    .collect(Collectors.toList()));

            sentboxTable.setOnMouseClicked(e -> {
                int focusedIndex = sentboxTable.getSelectionModel().getFocusedIndex();
                if (focusedIndex >= 0) {
                    sentMessages.get(focusedIndex).getCheck().get().fire();
                    sentboxTable.getSelectionModel().clearSelection();
                }
            });

            TreeTableColumn<MessageTableView, CheckBox> checkColumn = new TreeTableColumn<>("");
            TreeTableColumn<MessageTableView, String> senderColumn = new TreeTableColumn<>("To");
            TreeTableColumn<MessageTableView, String> sendTitleColumn = new TreeTableColumn<>("Title");
            TreeTableColumn<MessageTableView, String> sendDateColumn = new TreeTableColumn<>("Date");
            TreeTableColumn<MessageTableView, JFXButton> deleteButtonColumn = new TreeTableColumn<>("");

            checkColumn.setSortable(false);
            senderColumn.setSortable(false);
            sendTitleColumn.setSortable(false);
            sendDateColumn.setSortable(false);
            deleteButtonColumn.setSortable(false);

            sentboxTable.getColumns().addAll(checkColumn, senderColumn, sendTitleColumn, sendDateColumn, deleteButtonColumn);

            checkColumn.setCellValueFactory(u -> new SimpleObjectProperty(u.getValue().getValue().getCheck().get()));
            senderColumn.setCellValueFactory(m -> m.getValue().getValue().getReceiver());
            sendTitleColumn.setCellValueFactory(m -> m.getValue().getValue().getTitle());
            sendDateColumn.setCellValueFactory(m -> m.getValue().getValue().getSentDate());
            deleteButtonColumn.setCellValueFactory(u -> new SimpleObjectProperty(u.getValue().getValue().getDelete().get()));


            TreeItem<MessageTableView> sentBoxItem = new RecursiveTreeItem<MessageTableView>(sentMessages, RecursiveTreeObject::getChildren);

            sentboxTable.setRoot(sentBoxItem);
            sentboxTable.setShowRoot(false);
        }
    }

    /**
     * This method is deleting selected message in each table
     * @param u
     */
    public void deleteMessage(MessageTableView u) {
        try {
            messageService.delete(u.getId().get());
        } catch (MessageNotExistException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * This method perform disable delete button when anyone sent message is not selected
     * and enable button when anyone sent message is selected
     */
    private void disableSentboxDeleteButton() {
        Optional result = sentMessages.stream()
                .filter(p -> p.getCheck().get().isSelected())
                .map(p -> p.getCheck().get().isSelected()).findAny();
        if (result.isPresent()) {
            deleteMessages.setDisable(false);
        } else {
            deleteMessages.setDisable(true);
        }
    }

    /**
     * This method perform disable delete button when anyone received message is not selected
     * and enable button when anyone received message is selected
     */
    private void disableInboxDeleteButton() {
        Optional result = receivedMessages.stream()
                .filter(p -> p.getCheck().get().isSelected())
                .map(p -> p.getCheck().get().isSelected()).findAny();
        if (result.isPresent()) {
            deleteMessages.setDisable(false);
        } else {
            deleteMessages.setDisable(true);
        }
    }

    /**
     * This method is setting the reference to button responsible for deleting messages
     * @param delete
     */
    public void setDeleteMessages(JFXButton delete) {
        this.deleteMessages = delete;
    }

    /**
     * This method perform deleting of selected messages in inbox messages table
     * or sentbox messages table in admin dashboard view
     */
    public void deleteSelectedMessages(Tab selectedInbox, Tab selectedSendbox, JFXTreeTableView<MessageTableView> inboxTable,
                                       JFXTreeTableView<MessageTableView> sentboxTable) {
        deleteMessages.setOnAction(e -> {
            if (selectedInbox.isSelected()) {
                if (!receivedMessages.isEmpty()) {
                    selectedInboxMessagesIds = receivedMessages.stream()
                            .filter(u -> u.getCheck().get().isSelected())
                            .map(u -> u.getId().get()).collect(Collectors.toList());
                    messageService.delete(selectedInboxMessagesIds);
                }
                generateInboxTableView(inboxTable);
            }
            if (selectedSendbox.isSelected()) {
                if (!sentMessages.isEmpty()) {
                    selectedSentboxMessagesIds = sentMessages.stream()
                            .filter(u -> u.getCheck().get().isSelected())
                            .map(u -> u.getId().get()).collect(Collectors.toList());
                    messageService.delete(selectedSentboxMessagesIds);
                }
                generateSendBoxTableView(sentboxTable);
            }
            deleteMessages.setDisable(true);
        });
    }

    /**
     * This method are responsible for showing message window with more details about message
     *
     * @param id this is the id parameter to select in {@link MessageService} that we will ask about message
     *           with passed id after show
     */
    public void showMessageWindow(long id) {
        messageService.showMessageWindow(id);
    }
}
