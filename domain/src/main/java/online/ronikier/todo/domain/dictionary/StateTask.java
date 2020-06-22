package online.ronikier.todo.domain.dictionary;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import online.ronikier.todo.Messages;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum StateTask {
    NEW(Messages.STATE_TASK_NEW,"color:#DD6600;"),
    INITIALIZED(Messages.STATE_TASK_INITIALIZED,"color:#DD6600;"),
    MODIFIED(Messages.STATE_TASK_MODIFIED,"color:#006600;"),
    STARTED(Messages.STATE_TASK_STARTED,"color:#660000;"),
    ON_HOLD(Messages.STATE_TASK_ON_HOLD,"color:#000000;"),
    COMPLETED(Messages.STATE_TASK_COMPLETED,"color:#00DD00;");

    @Getter
    private String label;
    @Getter
    private String style;

}
