package com.project.manager.exceptions.registration;

/**
 * This exception is thrown when provided email is invalid
 */
public class EmailValidationException extends Exception {

    /**
     * This method is constructor of exception with already defined message
     */
    public EmailValidationException() {
        super("Email address is not valid");
    }
}
