package com.project.manager.exceptions.user;

public class UserDoesNotExistException extends RuntimeException {
    public UserDoesNotExistException(String s) {
        super(s);
    }
}
