package com.project.manager.exceptions.registration;

/**
 * This exception is thrown when user provides empty password
 */
public class EmptyPasswordException extends Exception {

    /**
     * This method is constructor of exception with already defined message
     */
    public EmptyPasswordException() {
        super("Inserted password is empty!");
    }
}
