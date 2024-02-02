package online.ronikier.todo.domain.exception;

import online.ronikier.todo.templete.SuperException;

/**
 * Data inconsistency.
 */
public class DataException extends SuperException {
    public DataException(String message) {
        super(message);
    }
}
