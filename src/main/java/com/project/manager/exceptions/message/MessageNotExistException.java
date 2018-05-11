package com.project.manager.exceptions.message;

public class MessageNotExistException extends Exception {
    public MessageNotExistException() {
        super("This message not exist");
    }
}
