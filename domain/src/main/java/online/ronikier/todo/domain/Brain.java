package online.ronikier.todo.domain;

import lombok.Data;
import online.ronikier.todo.domain.Person;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Data
public class Brain {
    private Person loggedPerson;
}
