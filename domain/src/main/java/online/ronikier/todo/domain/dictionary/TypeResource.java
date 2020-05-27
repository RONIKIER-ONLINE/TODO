package online.ronikier.todo.domain.dictionary;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import online.ronikier.todo.Messages;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum TypeResource {

    PERSON(Messages.TYPE_RESOURCE_PERSON),
    MONEY(Messages.TYPE_RESOURCE_MONEY),
    PICTURE(Messages.TYPE_RESOURCE_PICTURE),
    DOCUMENT(Messages.TYPE_RESOURCE_DOCUMENT),
    URL(Messages.TYPE_RESOURCE_URL),
    PHONE_NO(Messages.TYPE_RESOURCE_PHONE_NO),
    EMAIL(Messages.TYPE_RESOURCE_EMAIL);

    private String label;


}
