package com.project.manager.exceptions;

public class EmptyTaskDateException extends Exception {

    /**
     * This method is constructor of exception with already defined message
     */
    public EmptyTaskDateException() {
        super("Task date is empty!");
    }
}
