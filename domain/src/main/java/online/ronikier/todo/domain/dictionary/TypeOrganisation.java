package online.ronikier.todo.domain.dictionary;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import online.ronikier.todo.library.Messages;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum TypeOrganisation {
    OFFICE(Messages.TYPE_ORGANISATION_OFFICE),
    COMPANY(Messages.TYPE_ORGANISATION_COMPANY);

    private String label;

}
