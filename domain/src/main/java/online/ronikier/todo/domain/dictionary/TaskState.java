package online.ronikier.todo.domain.dictionary;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import online.ronikier.todo.Messages;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum TaskState {
    NEW(Messages.TASK_STATE_NEW,"color:#DD6600;"),
    INITIALIZED(Messages.TASK_STATE_INITIALIZED,"color:#DD6600;"),
    MODIFIED(Messages.TASK_STATE_MODIFIED,"color:Grey;"),
    STARTED(Messages.TASK_STATE_STARTED,"color:#006600;"),
    ON_HOLD(Messages.TASK_STATE_ON_HOLD,"color:#000000;"),
    COMPLETED(Messages.TASK_STATE_COMPLETED,"color:#00DD00;"),
    REJECTED(Messages.TASK_STATE_REJECTED,"color:#000000;");

    @Getter
    private String label;
    @Getter
    private String style;

}
