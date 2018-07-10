package com.project.manager.exceptions.login;

/**
 * This exception is thrown when user is providing different password that that assigned to his account
 */
public class WrongPasswordException extends Exception {

    /**
     * This method is constructor of exception with already defined message
     */
    public WrongPasswordException() {
        super("Wrong password!");
    }
}
