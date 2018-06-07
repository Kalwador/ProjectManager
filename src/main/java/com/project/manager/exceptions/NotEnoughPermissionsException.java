package com.project.manager.exceptions;

/**
 *
 */
public class NotEnoughPermissionsException extends Exception{
    public NotEnoughPermissionsException() {
        super("You don't have enough permissions to execute that action!");
    }
}