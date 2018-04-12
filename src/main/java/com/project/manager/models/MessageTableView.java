package com.project.manager.models;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.project.manager.entities.Message;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
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

    public static MessageTableView convert(Message message) {
        return MessageTableView
                .builder()
                .id(new SimpleLongProperty(message.getId()))
                .sender(new SimpleStringProperty(message.getSender()))
                .receiver(new SimpleStringProperty(message.getReceiver()))
                .title(new SimpleStringProperty(message.getTitle()))
                .contents(new SimpleStringProperty(message.getContents()))
                .sentDate(new SimpleStringProperty(message.getSentDate().format(DateTimeFormatter.ISO_DATE)))
                .build();
    }
}
