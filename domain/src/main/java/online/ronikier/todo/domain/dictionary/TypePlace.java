package online.ronikier.todo.domain.dictionary;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import online.ronikier.todo.library.Messages;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum TypePlace {

    CITY(Messages.TYPE_PLACE_CITY),
    BUILDING(Messages.TYPE_PLACE_BUILDING),
    ;

    private String label;

}
