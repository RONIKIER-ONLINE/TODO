package online.ronikier.todo.domain.exception;

import online.ronikier.todo.templete.SuperException;

/**
 * Similar task exists for some reason :-/
 */
public class TaskExistsException extends SuperException {
    public TaskExistsException(String message) {
        super(message);
    }
}
