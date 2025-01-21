package ru.DTF98.taskManagementSystem.exception;

public class NotFoundUserException extends RuntimeException {

    public NotFoundUserException(String msg) {
        super(msg);
    }
}