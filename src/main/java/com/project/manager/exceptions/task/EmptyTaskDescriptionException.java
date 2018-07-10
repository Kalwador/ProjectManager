package com.project.manager.exceptions.task;

public class EmptyTaskDescriptionException extends Exception {

    /**
     * This method is constructor of exception with already defined message
     */
    public EmptyTaskDescriptionException() {
        super("Task description is empty!");
    }
}
