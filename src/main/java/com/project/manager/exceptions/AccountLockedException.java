package com.project.manager.exceptions;

/**
 * This is an exception which is throwing when account is locked by admin
 */
public class AccountLockedException extends Exception {

    /**
     * This is a constructor of exception and default specified message
     */
    public AccountLockedException() {
        super("You account is locked, you should contact with administrator to unlock your account!");
    }
}
