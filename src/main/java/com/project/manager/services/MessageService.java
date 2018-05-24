package com.project.manager.services;

import com.project.manager.controllers.message.MessageViewWindowController;
import com.project.manager.entities.Message;
import com.project.manager.entities.UserModel;
import com.project.manager.exceptions.message.MessageNotExistException;
import com.project.manager.exceptions.user.UserDoesNotExistException;
import com.project.manager.models.MessageTableView;
import com.project.manager.repositories.MessageRepository;
import com.project.manager.repositories.UserRepository;
import com.project.manager.ui.AlertManager;
import com.project.manager.ui.sceneManager.SceneManager;
import com.project.manager.ui.sceneManager.SceneType;
import lombok.extern.log4j.Log4j;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * This is the class which contain all message logic method
 */
@Log4j
@Service
public class MessageService {

    private MessageRepository messageRepository;
    private SessionService sessionService;
    private SceneManager sceneManager;


    private UserRepository userRepository;

    /**
     * Constructor of this class contain reference to {@link SessionService} {@link SceneManager} and injected
     * message repository
     *
     * @param messageRepository this repository provides all logical method of database to manager {@link Message} in database
     */
    @Autowired
    public MessageService(MessageRepository messageRepository, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.sessionService = SessionService.getInstance();
        this.sceneManager = SceneManager.getInstance();
    }

    /**
     * That method return all method which was received to actual logged user
     *
     * @return the list of {@link Message}
     */
    public List<Message> getAllReceivedMessages() {
        return messageRepository.findByReceiver(sessionService.getUserModel().getUsername());
    }

    /**
     * Method which return all messages which was sent by actual logged user
     *
     * @return list of {@link Message}
     */
    public List<Message> getAllSentMessages() {
        return messageRepository.findBySender(sessionService.getUserModel().getUsername());
    }

    /**
     * Method which showing the message view window to display more information about message which passed id
     *
     * @param id parameter contain id of {@link Message} which user want to see
     */
    public void showMessageWindow(long id) {
        Optional<Message> message = Optional.of(messageRepository.getOne(id));
        message.ifPresent(m -> {
            MessageViewWindowController.messageToView = MessageTableView.convert(m);
            sceneManager.showInNewWindow(SceneType.MESSAGE_VIEW_WINDOW);
        });
    }


    public void sentMessage(Message message) throws UserDoesNotExistException {
        UserModel sender = sessionService.getUserModel();
        Optional<UserModel> receiver = userRepository.findByUsername(message.getReceiver());
        if (!receiver.isPresent()) {
            throw new UserDoesNotExistException();
        }

        message = messageRepository.save(message);
        message.setSender(sender.getUsername());
        message.getUsers().add(sender);
        message.getUsers().add(receiver.get());
        sender.getMessages().add(message);
        receiver.get().getMessages().add(message);
        messageRepository.save(message);
        log.info("The message was sent to '" + message.getSender() + "' from '" + message.getReceiver());
    }

    /**
     * This method perform delete message operation on messageRepository by inserted message Id in parameter
     *
     * @param messageId Id of message that we need to delete
     */
    public void delete(Long messageId) throws MessageNotExistException {
        Optional<Message> optionalMessage = messageRepository.findById(messageId);
        if (optionalMessage.isPresent()) {
            Alert alert = AlertManager.showConfirmationAlert("Delete", "Do you really want to delete that project?");
            if (alert.getResult().equals(ButtonType.OK)) {
                messageRepository.delete(optionalMessage.get());
            }
        } else {
            throw new MessageNotExistException();
        }
    }

    /**
     * This method perform delete list of messages operation on messageRepository by inserted list of
     * message indexes in parameter.
     *
     * @param indexes indexes of messages that we need to delete
     */
    public void delete(List<Long> indexes) {
        Alert alert = AlertManager.showConfirmationAlert("Delete", "Do you really want to delete this " + indexes.size() + " message(s)?");
        if (alert.getResult().equals(ButtonType.OK)) {
            for (Long id : indexes) {
                messageRepository.delete(messageRepository.findById(id).get());
            }
        }
    }
}