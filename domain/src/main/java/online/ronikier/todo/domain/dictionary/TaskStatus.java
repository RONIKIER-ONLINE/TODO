package online.ronikier.todo.domain.dictionary;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import online.ronikier.todo.Messages;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum TaskStatus {

    OK(Messages.TASK_STATUS_OK,"color:Grey;"),
    TODAY(Messages.TASK_STATUS_TODAY,"color:#660000;"),
    APPROACHING(Messages.TASK_STATUS_APPROACHING,"color:#660000;"),
    DELAYED(Messages.TASK_STATUS_DELAYED,"color:#FF0000;"),
    UNKNOWN(Messages.TASK_STATUS_UNKNOWN,"color:#DD6600;");


    @Getter
    private String label;
    @Getter
    private String style;

}
