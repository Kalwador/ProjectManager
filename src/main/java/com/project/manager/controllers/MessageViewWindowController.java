package com.project.manager.controllers;

import com.project.manager.models.MessageTableView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

@Component
@Getter
@Setter
public class MessageViewWindowController implements Initializable {

    @FXML
    private Label sender;
    @FXML
    private Label contents;
    @FXML
    private Label receiver;
    @FXML
    private Label date;
    @FXML
    private Label title;

    public static MessageTableView messageToView;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setLabelsContents();
    }

    public void setLabelsContents() {
        if (Optional.ofNullable(messageToView).isPresent()) {
            sender.setText(messageToView.getSender().get());
            receiver.setText(messageToView.getReceiver().get());
            title.setText(messageToView.getTitle().get());
            date.setText(messageToView.getSentDate().get());
            contents.setText(messageToView.getContents().get());
        }
    }
}
