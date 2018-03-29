package com.project.manager.services;

import com.project.manager.controllers.MessageViewWindowController;
import com.project.manager.entities.Message;
import com.project.manager.models.MessageTableView;
import com.project.manager.repositories.MessageRepository;
import com.project.manager.sceneManager.SceneManager;
import com.project.manager.sceneManager.SceneType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * This is the class which contain all message logic method
 */
@Service
public class MessageService {

    private MessageRepository messageRepository;

    private SessionService sessionService;

    private SceneManager sceneManager;

    /**
     * Constructor of this class contain reference to {@link SessionService} {@link SceneManager} and injected
     * message repository
     * @param messageRepository this repository provides all logical method of database to manager {@link Message} in database
     */
    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
        this.sessionService = SessionService.getInstance();
        this.sceneManager = SceneManager.getInstance();
    }

    /**
     * That method return all method which was received to actual logged user
     * @return the list of {@link Message}
     */
    public List<Message> getAllReceivedMessages() {
        return messageRepository.findByReceiver(sessionService.getEmail()).get();
    }

    /**
     * Method which return all messages which was sent by actual logged user
     * @return list of {@link Message}
     */
    public List<Message> getAllSentMessages() {
        return messageRepository.findBySender(sessionService.getEmail()).get();
    }

    /**
     * Method which showing the message view window to display more information about message which passed id
     * @param id parameter contain id of {@link Message} which user want to see
     */
    public void showMessageWindow(long id) {
        Optional<Message> message = Optional.of(messageRepository.getOne(id));
        message.ifPresent(m -> {
            MessageViewWindowController.messageToView = MessageTableView.convert(m);
            sceneManager.showInNewWindow(SceneType.MESSAGE_VIEW_WINDOW);
        });
    }
}
