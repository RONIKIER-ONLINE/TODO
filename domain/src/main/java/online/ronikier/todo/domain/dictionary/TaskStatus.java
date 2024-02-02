package online.ronikier.todo.domain.dictionary;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import online.ronikier.todo.Messages;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum TaskStatus {

    DELAYED(Messages.TASK_STATUS_DELAYED,"color:Black;"),
    TODAY(Messages.TASK_STATUS_TODAY,"color:Red;"),
    TOMMOROW(Messages.TASK_STATUS_TOMMOROW,"color:Orange;"),
    NEXT_WEEK(Messages.TASK_STATUS_NEXT_WEEK,"color:Yellow;"),
    NEXT_WEEKEND(Messages.TASK_STATUS_NEXT_WEEKEND,"color:Blue;"),
    NEXT_MONTH(Messages.TASK_STATUS_NEXT_MONTH,"color:Purple;"),
    OK(Messages.TASK_STATUS_OK,"color:Green;");
//    THIS_WEEK(Messages.TASK_STATUS_THIS_WEEK,"color:Yellow;"),
//    APPROACHING(Messages.TASK_STATUS_APPROACHING,"color:Grey;");

    @Getter
    private String label;
    @Getter
    private String style;

}
