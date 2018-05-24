package com.project.manager.exceptions;

/**
 * This exception is thrown when user provides empty activation code
 */
public class EmptyGeneratedCodeException extends Exception {

    /**
     * This method is constructor of exception with already defined message
     */
    public EmptyGeneratedCodeException() {
        super("Inserted code is empty!");
    }
}
