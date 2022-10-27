package online.ronikier.todo.domain;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class Brain {
    private Person loggedPerson;
}
