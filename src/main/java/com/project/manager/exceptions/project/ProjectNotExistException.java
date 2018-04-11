package com.project.manager.exceptions.project;


public class ProjectNotExistException extends RuntimeException {
    public ProjectNotExistException(String s) {
        super(s);
    }
}
