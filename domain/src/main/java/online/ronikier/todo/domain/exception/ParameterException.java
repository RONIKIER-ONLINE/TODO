package online.ronikier.todo.domain.exception;

import online.ronikier.todo.templete.SuperException;

/**
 * Parameter not OK ;)
 */
public class ParameterException extends SuperException {
    public ParameterException(String message) {
        super(message);
    }
}
