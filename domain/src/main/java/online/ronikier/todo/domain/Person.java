package online.ronikier.todo.domain;

import lombok.*;
import online.ronikier.todo.templete.SuperEntity;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ToString
@Data
@NoArgsConstructor
@AllArgsConstructor
@NodeEntity
public class Person extends SuperEntity {

    @Relationship(type = "KNOWS")
    protected List<Person> knownPersons;

    @NonNull
    @Size(min = 6, max = 10)
    private String username;

    @NonNull
    @Size(min = 6, max = 10)
    private String password;

    public void knows(Person person) {
        if (getKnownPersons() == null) {
            setKnownPersons(new ArrayList<>());
        }
        getKnownPersons().add(person);
    }

    @Override
    public String toString() {
        return "'" + this.username + "' requires: " +
                Optional.ofNullable(this.getKnownPersons()).orElse(Collections.emptyList())
                        .stream()
                        .map(Person::getUsername)
                        .collect(Collectors.toList());
    }

    public List<Person> getKnownPersons() {
        if (knownPersons == null) {
            setKnownPersons(new ArrayList<>());
        }
        return knownPersons;
    }

}
