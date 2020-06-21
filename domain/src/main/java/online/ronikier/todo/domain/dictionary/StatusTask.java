package online.ronikier.todo.domain.dictionary;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import online.ronikier.todo.Messages;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum StatusTask {

    OK(Messages.STATUS_TASK_OK),
    APPROACHING(Messages.STATUS_TASK_APPROACHING),
    DELAYED(Messages.STATUS_TASK_DELAYED);

    private String label;

    public String getLabel() {
        return label;
    }

}
