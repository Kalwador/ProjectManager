package com.project.manager.exceptions.user;

/**
 * This is exception which is throwing when specified user does not exist in database
 */
public class UserDoesNotExistException extends Exception {

    /**
     * Only constructor which default message
     */
    public UserDoesNotExistException() {
        super("This user does not exist!");
    }
}
