package com.project.manager.exceptions;

/**
 * This exception is thrown when user try to provide empty username
 */
public class EmptyUsernameException extends Exception {

    /**
     * This method is constructor of exception with already defined message
     */
    public EmptyUsernameException() {
        super("Inserted username is empty!");
    }
}
