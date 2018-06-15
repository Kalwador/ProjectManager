package com.project.manager.exceptions;

public class EmptyTaskPriorityException extends Exception {

    /**
     * This method is constructor of exception with already defined message
     */
    public EmptyTaskPriorityException() {
        super("Task priority is empty!");
    }
}
