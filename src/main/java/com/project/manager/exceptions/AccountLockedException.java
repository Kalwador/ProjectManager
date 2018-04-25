package com.project.manager.exceptions;

/**
 * This is an exception which is throwing when account is locked by admin
 */
public class AccountLockedException extends RuntimeException {

    /**
     * This is a constructor of exception to specify the message of error
     * @param message message of error
     */
    public AccountLockedException(String message) {
        super(message);
    }
}
