package online.ronikier.todo.domain.dictionary;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import online.ronikier.todo.Messages;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum TypeTask {

    GENERAL(Messages.TYPE_TASK_GENERAL),
    PERSONAL(Messages.TYPE_TASK_PERSONAL),
    MONEY(Messages.TYPE_TASK_MONEY);

    @Getter
    private String label;

}
