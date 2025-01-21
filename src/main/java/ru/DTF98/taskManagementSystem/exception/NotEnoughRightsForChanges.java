package ru.DTF98.taskManagementSystem.exception;

public class NotEnoughRightsForChanges extends RuntimeException {
    public NotEnoughRightsForChanges(String msg) {
        super(msg);
    }
}
