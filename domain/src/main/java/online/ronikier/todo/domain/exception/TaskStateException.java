package online.ronikier.todo.domain.exception;

import online.ronikier.todo.templete.SuperException;

public class TaskStateException extends SuperException {
    public TaskStateException(String message) {
        super(message);
    }
}

