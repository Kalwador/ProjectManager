package com.project.manager.exceptions;

/**
 * That exception is throwing when user provide different activation code than that sent on his mail
 */
public class DifferentGeneratedCodeException extends Exception {

    /**
     * This method is constructor of exception with already defined message
     */
    public DifferentGeneratedCodeException() {
        super("The code is different than generated code!");
    }
}
