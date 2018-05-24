package com.project.manager.services;

import com.project.manager.entities.Message;
import com.project.manager.entities.UserModel;
import com.project.manager.exceptions.user.UserDoesNotExistException;
import com.project.manager.repositories.MessageRepository;
import com.project.manager.repositories.UserRepository;
import com.project.manager.ui.sceneManager.SceneManager;
import com.project.manager.ui.sceneManager.SceneType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class MessageServiceTest {

    @Mock
    private MessageRepository messageRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private MessageService messageService;

    @Mock
    private SessionService sessionService;

    @Mock
    private SceneManager sceneManager;

    @Before
    public void prepareSession() {
        sessionService = SessionService.getInstance();
    }

    /**
     * TODO
     */
    @Test
    public void getAllReceivedMessagesTest() {
        String username = getExampleMessages().get(0).getReceiver();
        sessionService.setUserModel(UserModel.builder().username(username).build());

        when(messageRepository.findByReceiver(username)).thenReturn(getExampleMessages());

        List<Message> receivedList = messageService.getAllReceivedMessages();

        assertNotNull(receivedList);
        assertEquals(receivedList.get(0).getUsers(), getExampleMessages().get(0).getUsers());
        assertEquals(receivedList.get(0).getId(), getExampleMessages().get(0).getId());
        assertEquals(receivedList.get(0).getReceiver(), getExampleMessages().get(0).getReceiver());
        assertEquals(receivedList.get(0).getTitle(), getExampleMessages().get(0).getTitle());
        assertEquals(receivedList.get(0).getContents(), getExampleMessages().get(0).getContents());
        assertEquals(receivedList.get(0).getSentDate().format(DateTimeFormatter.ISO_DATE),
                getExampleMessages().get(0).getSentDate().format(DateTimeFormatter.ISO_DATE));
    }

    /**
     * TODO
     */
    @Test
    public void getAllSentMessagesTest() {
        String username = getExampleMessages().get(0).getReceiver();
        sessionService.setUserModel(UserModel.builder().username(username).build());

        when(messageRepository.findBySender(username)).thenReturn(getExampleMessages());

        List<Message> sentList = messageService.getAllSentMessages();

        assertNotNull(sentList);
        assertEquals(sentList.get(0).getUsers(), getExampleMessages().get(0).getUsers());
        assertEquals(sentList.get(0).getId(), getExampleMessages().get(0).getId());
        assertEquals(sentList.get(0).getSender(), getExampleMessages().get(0).getSender());
        assertEquals(sentList.get(0).getTitle(), getExampleMessages().get(0).getTitle());
        assertEquals(sentList.get(0).getContents(), getExampleMessages().get(0).getContents());
        assertEquals(sentList.get(0).getSentDate().format(DateTimeFormatter.ISO_DATE),
                getExampleMessages().get(0).getSentDate().format(DateTimeFormatter.ISO_DATE));
    }

    @Test
    public void showMessageWindowTest() {
        Message message = getExampleMessages().get(0);

        when(messageRepository.getOne(message.getId())).thenReturn(message);
        doNothing().when(sceneManager).showInNewWindow(SceneType.MESSAGE_VIEW_WINDOW);
    }

//    @Test(expected = UserDoesNotExistException.class)
//    public void testSentMessageExpectUserDoesNotFoundException() {
//        Message message = getExampleMessages().get(0);
//        when(userRepository.findByUsername(message.getReceiver())).thenReturn(Optional.empty());
//        messageService.sentMessage(message);
//    }
//
//     @Test
//     public void testSentMessage() {
//        Message message = getExampleMessages().get(0);
//        UserModel receiver = UserModel.builder().messages(new HashSet<>()).build();
//        UserModel sender = UserModel.builder().username("actualSender").messages(new HashSet<>()).build();
//        sessionService.setLoggedUser(sender);
//
//        when(userRepository.findByUsername(message.getReceiver())).thenReturn(Optional.of(receiver));
//        when(messageRepository.save(message)).thenReturn(message);
//        messageService.sentMessage(message);
//
//        assertNotNull(message);
//        assertNotNull(receiver);
//        assertNotNull(sender);
//
//        assertTrue(message.getUsers().contains(receiver));
//        assertTrue(message.getUsers().contains(sender));
//        assertEquals(message.getUsers().size(), 2);
//
//        assertTrue(receiver.getMessages().contains(message));
//        assertEquals(receiver.getMessages().size(), 1);
//        assertTrue(sender.getMessages().contains(message));
//        assertEquals(sender.getMessages().size(), 1);
//
//        assertEquals(message.getId().intValue(), 1);
//        assertEquals(message.getSender(), "actualSender");
//        assertEquals(message.getReceiver(), "receiver");
//        assertEquals(message.getTitle(), "title");
//        assertEquals(message.getContents(), "contents");
//        assertEquals(message.getSentDate().format(DateTimeFormatter.ISO_LOCAL_DATE),
//                LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE));
//     }

    public List<Message> getExampleMessages() {
        List<Message> messages = new ArrayList<Message>();
        messages.add(Message
                .builder()
                .id(1L)
                .sender("sender")
                .receiver("receiver")
                .title("title")
                .contents("contents")
                .sentDate(LocalDateTime.now())
                .users(new HashSet<>())
                .build());

        messages.add(Message
                .builder()
                .id(2L)
                .sender("sender2")
                .receiver("receiver2")
                .title("title2")
                .contents("contents2")
                .sentDate(LocalDateTime.now())
                .users(new HashSet<>())
                .build());
        return messages;
    }
}
