package com.project.manager.exceptions.message;

/**
 * This is the exception which is throwing when someone is trying to request about message which does not exist
 */
public class MessageNotExistException extends Exception {

    /**
     * This method is constructor of exception with already defined message
     */
    public MessageNotExistException() {
        super("This message not exist");
    }
}
