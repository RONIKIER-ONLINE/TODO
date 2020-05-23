package online.ronikier.todo.domain.dictionary;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import online.ronikier.todo.domain.configuration.Messages;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum TypePerson {

    PUBLIC(Messages.TYPE_PERSON_PUBLIC),
    FAMILY(Messages.TYPE_PERSON_FAMILY),
    HOSTILE(Messages.TYPE_PERSON_HOSTILE);

    private String label;


}
