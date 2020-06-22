package online.ronikier.todo.domain.dictionary;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import online.ronikier.todo.Messages;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum StatusTask {

    OK(Messages.STATUS_TASK_OK,"color:#006600;"),
    APPROACHING(Messages.STATUS_TASK_APPROACHING,"color:#DD6600;"),
    DELAYED(Messages.STATUS_TASK_DELAYED,"color:#660000;"),
    UNKNOWN(Messages.STATUS_TASK_UNKNOWN,"color:#000066;");

    @Getter
    private String label;
    @Getter
    private String style;

}
