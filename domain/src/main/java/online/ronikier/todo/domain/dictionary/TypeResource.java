package online.ronikier.todo.domain.dictionary;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import online.ronikier.todo.library.Messages;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum TypeResource {

    PERSON(Messages.TYPE_RESOURCE_PERSON),
    MONEY(Messages.TYPE_RESOURCE_MONEY),
    PICTURE(Messages.TYPE_RESOURCE_PICTURE),
    DOCUMENT(Messages.TYPE_RESOURCE_DOCUMENT),
    TYPE_RESOURCE_URL(Messages.TYPE_RESOURCE_URL),
    TYPE_RESOURCE_PHONE_NO(Messages.TYPE_RESOURCE_PHONE_NO),
    TYPE_RESOURCE_EMAIL(Messages.TYPE_RESOURCE_EMAIL);

    private String label;


}
