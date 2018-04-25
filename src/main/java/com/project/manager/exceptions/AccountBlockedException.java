package com.project.manager.exceptions;

/**
 * This is an exception which is throwing when account is blocked by own bad operation in service
 */
public class AccountBlockedException extends RuntimeException {

    /**
     * This method is constructor of exception
     * @param message this is parameter of error message
     */
    public AccountBlockedException(String message) {
        super(message);
    }
}
