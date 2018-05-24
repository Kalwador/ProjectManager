package com.project.manager.exceptions.project;

/**
 * This exception is throwing when some project does not exist in database
 */
public class ProjectNotExistException extends Exception {

    /**
     * The default constructor with also default message for that kind of exception
     */
    public ProjectNotExistException() {
        super("This project does not exist!");
    }
}
