package com.project.manager.exceptions;

/**
 * That exception is throwing in some fatal cases which could be dangerous for application
 */
public class FatalError extends Exception {

    /**
     * This is the constructor to create and specify message of that error
     * @param message this is the provided message of case when this exception is throwing
     */
    public FatalError(String message) {
        super(message);
    }
}