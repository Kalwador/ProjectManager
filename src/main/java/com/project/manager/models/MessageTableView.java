package com.project.manager.models;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.project.manager.entities.Message;
import com.project.manager.ui.GraphicButtonGenerator;
import javafx.beans.property.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.format.DateTimeFormatter;

@Setter
@Getter
@Builder
public class MessageTableView extends RecursiveTreeObject<MessageTableView> {

    private LongProperty id;

    private StringProperty sender;

    private StringProperty receiver;

    private StringProperty title;

    private StringProperty contents;

    private StringProperty sentDate;

    private SimpleObjectProperty<JFXButton> delete;

    private SimpleObjectProperty<JFXCheckBox> check;

    /**
     * This is the method which convert original Message entity from database to our class for making pretty view class
     * to displaying information about message for admin
     *
     * @param message This parameter is original message for converting to Message view class
     * @return method return already converted original message to message view class
     */
    public static MessageTableView convert(Message message) {
        return MessageTableView
                .builder()
                .id(new SimpleLongProperty(message.getId()))
                .sender(new SimpleStringProperty(message.getSender()))
                .receiver(new SimpleStringProperty(message.getReceiver()))
                .title(new SimpleStringProperty(message.getTitle()))
                .contents(new SimpleStringProperty(message.getContents()))
                .sentDate(new SimpleStringProperty(message.getSentDate().format(DateTimeFormatter.ISO_DATE)))
                .check(new SimpleObjectProperty<>(new JFXCheckBox()))
                .build();
    }

    /**
     * This is the method to generate delete button in table
     *
     * @param messageTableView this is the messageView object for modify it to contain this button inside
     * @return method will return MessageView object with delete button inside
     */
    public MessageTableView generateDelButton(MessageTableView messageTableView) {
        JFXButton del = new GraphicButtonGenerator().getJfxButtonWithGraphic("/images/delete.png");
        messageTableView.setDelete(new SimpleObjectProperty<>(del));
        return messageTableView;
    }
}
