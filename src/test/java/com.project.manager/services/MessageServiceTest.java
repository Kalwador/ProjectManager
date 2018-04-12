package com.project.manager.services;

import com.project.manager.entities.Message;
import com.project.manager.entities.UserModel;
import com.project.manager.repositories.MessageRepository;
import com.project.manager.ui.sceneManager.SceneManager;
import com.project.manager.ui.sceneManager.SceneType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class MessageServiceTest {

    @Mock
    private MessageRepository messageRepository;

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
        String email = getExampleMessages().get().get(0).getReceiver();
        sessionService.setLoggedUser(UserModel.builder().email(email).build());

        when(messageRepository.findByReceiver(email)).thenReturn(getExampleMessages());

        List<Message> receivedList = messageService.getAllReceivedMessages();

        assertNotNull(receivedList);
        assertEquals(receivedList.get(0).getUsers(), getExampleMessages().get().get(0).getUsers());
        assertEquals(receivedList.get(0).getId(), getExampleMessages().get().get(0).getId());
        assertEquals(receivedList.get(0).getReceiver(), getExampleMessages().get().get(0).getReceiver());
        assertEquals(receivedList.get(0).getTitle(), getExampleMessages().get().get(0).getTitle());
        assertEquals(receivedList.get(0).getContents(), getExampleMessages().get().get(0).getContents());
        assertEquals(receivedList.get(0).getSentDate().format(DateTimeFormatter.ISO_DATE),
                getExampleMessages().get().get(0).getSentDate().format(DateTimeFormatter.ISO_DATE));
    }

    /**
     * TODO
     */
    @Test
    public void getAllSentMessagesTest() {
        String email = getExampleMessages().get().get(0).getReceiver();
        sessionService.setLoggedUser(UserModel.builder().email(email).build());

        when(messageRepository.findBySender(email)).thenReturn(getExampleMessages());

        List<Message> sentList = messageService.getAllSentMessages();

        assertNotNull(sentList);
        assertEquals(sentList.get(0).getUsers(), getExampleMessages().get().get(0).getUsers());
        assertEquals(sentList.get(0).getId(), getExampleMessages().get().get(0).getId());
        assertEquals(sentList.get(0).getSender(), getExampleMessages().get().get(0).getSender());
        assertEquals(sentList.get(0).getTitle(), getExampleMessages().get().get(0).getTitle());
        assertEquals(sentList.get(0).getContents(), getExampleMessages().get().get(0).getContents());
        assertEquals(sentList.get(0).getSentDate().format(DateTimeFormatter.ISO_DATE),
                getExampleMessages().get().get(0).getSentDate().format(DateTimeFormatter.ISO_DATE));
    }

    @Test
    public void showMessageWindowTest() {
        Message message = getExampleMessages().get().get(0);

        when(messageRepository.getOne(message.getId())).thenReturn(message);
        doNothing().when(sceneManager).showInNewWindow(SceneType.MESSAGE_VIEW_WINDOW);
    }

    public Optional<List<Message>> getExampleMessages() {
        Optional<List<Message>> messages = Optional.of(new ArrayList<Message>());
        messages.get().add(Message
                .builder()
                .id(1L)
                .sender("sender@mail.com")
                .receiver("receiver@mail.com")
                .title("title")
                .contents("contents")
                .sentDate(LocalDateTime.now())
                .build());

        messages.get().add(Message
                .builder()
                .id(2L)
                .sender("sender2@mail.com")
                .receiver("receiver2@mail.com")
                .title("title2")
                .contents("contents2")
                .sentDate(LocalDateTime.now())
                .build());
        return messages;
    }
}
