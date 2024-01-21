package online.ronikier.todo.domain.dictionary;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import online.ronikier.todo.Messages;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum TaskState {
    STARTED(Messages.TASK_STATE_STARTED,"color:#006600;"),
    ON_HOLD(Messages.TASK_STATE_ON_HOLD,"color:#DD6600;"),
    REJECTED(Messages.TASK_STATE_REJECTED,"color:#000000;"),
    COMPLETED(Messages.TASK_STATE_COMPLETED,"color:#00DD00;");

    @Getter
    private String label;
    @Getter
    private String style;

}
