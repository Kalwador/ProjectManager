package com.project.manager.exceptions.user;

/**
 * This exception is throwing when specified user already exist in database
 */
public class UserAlreadyExistException extends Exception {

    /**
     * This is default constructor with already defined message
     */
    public UserAlreadyExistException() {
        super("The user with that email or username or id already exist in our service");
    }
}
