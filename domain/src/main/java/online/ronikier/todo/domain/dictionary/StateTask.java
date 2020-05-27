package online.ronikier.todo.domain.dictionary;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import online.ronikier.todo.library.Messages;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum StateTask {
    STARTED(Messages.STATE_TASK_STARTED),
    ON_HOLD(Messages.STATE_TASK_ON_HOLD),
    COMPLETED(Messages.STATE_TASK_COMPLETED);

    private String label;

}
