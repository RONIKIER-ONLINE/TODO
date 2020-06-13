package online.ronikier.todo.domain.dictionary;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import online.ronikier.todo.Messages;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum TypeTask {

    GENERAL(Messages.TYPE_TASK_GENERAL),
    PERSONAL(Messages.TYPE_TASK_PERSONAL),
    MONEY(Messages.TYPE_TASK_MONEY);

    private String label;

    public String getLabel() {
        return label;
    }
}
