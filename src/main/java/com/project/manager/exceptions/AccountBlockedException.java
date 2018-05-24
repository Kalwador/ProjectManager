package com.project.manager.exceptions;

/**
 * This is an exception which is throwing when account is blocked by own bad operation in service
 */
public class AccountBlockedException extends Exception {

    /**
     * This method is constructor of exception with already defined message
     */
    public AccountBlockedException() {
        super("This account is blocked, and can be unblock by user!");
    }
}
