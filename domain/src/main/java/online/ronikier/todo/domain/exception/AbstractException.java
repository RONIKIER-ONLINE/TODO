package online.ronikier.todo.domain.exception;

public abstract class AbstractException extends Throwable {
    public AbstractException(String message) {
        super(message);
    }
}
