package online.ronikier.todo.domain.dictionary;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import online.ronikier.todo.Messages;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum StateTask {
    INITIALIZED(Messages.STATE_TASK_INITIALIZED),
    MODIFIED(Messages.STATE_TASK_MODIFIED),
    STARTED(Messages.STATE_TASK_STARTED),
    ON_HOLD(Messages.STATE_TASK_ON_HOLD),
    COMPLETED(Messages.STATE_TASK_COMPLETED);

    private String label;

    public String getLabel() {
        return label;
    }

}
