package online.ronikier.todo.domain.dictionary;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import online.ronikier.todo.Messages;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum TaskType {

    GENERAL(Messages.TASK_TYPE_GENERAL),
    PERSONAL(Messages.TASK_TYPE_PERSONAL),
    MONEY(Messages.TASK_TYPE_MONEY);

    @Getter
    private String label;

}
