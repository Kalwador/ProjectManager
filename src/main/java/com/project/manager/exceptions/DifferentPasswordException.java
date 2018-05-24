package com.project.manager.exceptions;

/**
 * This exception is thrown when user is providing different password that that assigned to his account
 */
public class DifferentPasswordException extends Exception {

    /**
     * This method is constructor of exception with already defined message
     */
    public DifferentPasswordException() {
        super("Passwords are different!");
    }
}
