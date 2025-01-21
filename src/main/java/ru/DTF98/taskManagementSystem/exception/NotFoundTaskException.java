package ru.DTF98.taskManagementSystem.exception;

public class NotFoundTaskException extends RuntimeException {
    public NotFoundTaskException(String msg) {
        super(msg);
    }
}
