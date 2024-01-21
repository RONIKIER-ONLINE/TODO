package online.ronikier.todo.domain.dictionary;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import online.ronikier.todo.Messages;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum TaskStatus {

    TODAY(Messages.TASK_STATUS_TODAY,"color:Red;"),
    TOMMOROW(Messages.TASK_STATUS_TOMMOROW,"color:Orange;"),
    THIS_WEEK(Messages.TASK_STATUS_THIS_WEEK,"color:Yellow;"),
    APPROACHING(Messages.TASK_STATUS_APPROACHING,"color:Purple;"),
    DELAYED(Messages.TASK_STATUS_DELAYED,"color:Black;"),
    OK(Messages.TASK_STATUS_OK,"color:Green;");


    @Getter
    private String label;
    @Getter
    private String style;

}
