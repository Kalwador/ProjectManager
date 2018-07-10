package com.project.manager.exceptions.task;

public class EmptyTaskTagException extends Exception {

    /**
     * This method is constructor of exception with already defined message
     */
    public EmptyTaskTagException() {
        super("Task tag is empty!");
    }
}