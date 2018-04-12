package com.project.manager.exceptions;

public class NotEnoughPermissionsException extends RuntimeException{
    public NotEnoughPermissionsException(String s) {
        super(s);
    }
}