package com.project.manager.controllers.message;

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

/**
 * This is the controller to window responsible for showing all data about selected message in table
 */
@Component
@Getter
@Setter
public class MessageViewWindowController implements Initializable {

    /**
     * All below field are label which will store information about selected message
     */
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

    /**
     * This is provided message used to generate view
     */
    public static MessageTableView messageToView;

    /**
     * This method is responsible for manage all components and view of the scene
     * @param location default framework parameter
     * @param resources default framework parameter
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setLabelsContents();
    }

    /**
     * That method is setting information about selected message in above labels
     */
    private void setLabelsContents() {
        if (Optional.ofNullable(messageToView).isPresent()) {
            sender.setText(messageToView.getSender().get());
            receiver.setText(messageToView.getReceiver().get());
            title.setText(messageToView.getTitle().get());
            date.setText(messageToView.getSentDate().get());
            contents.setText(messageToView.getContents().get());
        }
    }
}
