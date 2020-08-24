package online.ronikier.todo.domain.exception;

import online.ronikier.todo.templete.SuperException;

public class TaskExistsException extends SuperException {
    public TaskExistsException(String message) {
        super(message);
    }
}
