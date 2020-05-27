package online.ronikier.todo.domain.dictionary;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import online.ronikier.todo.library.Messages;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum TypeTask {

    PERSONAL(Messages.TYPE_TASK_PERSONAL),
    MONEY(Messages.TYPE_TASK_MONEY);

    private String label;

}
