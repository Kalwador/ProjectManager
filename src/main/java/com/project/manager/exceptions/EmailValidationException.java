package com.project.manager.exceptions;

public class EmailValidationException extends Exception {
    public EmailValidationException() {
        super("Email adress is not valid");
    }
}
