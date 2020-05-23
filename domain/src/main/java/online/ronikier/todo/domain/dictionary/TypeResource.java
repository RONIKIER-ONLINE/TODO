package online.ronikier.todo.domain.dictionary;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import online.ronikier.todo.domain.configuration.Messages;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum TypeResource {

    PERSON(Messages.TYPE_RESOURCE_PERSON),
    MONEY(Messages.TYPE_RESOURCE_MONEY);

    private String label;


}
