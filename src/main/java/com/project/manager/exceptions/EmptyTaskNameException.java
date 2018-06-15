package com.project.manager.exceptions;

public class EmptyTaskNameException extends Exception {

    /**
     * This method is constructor of exception with already defined message
     */
    public EmptyTaskNameException() {
        super("Task name is empty!");
    }
}