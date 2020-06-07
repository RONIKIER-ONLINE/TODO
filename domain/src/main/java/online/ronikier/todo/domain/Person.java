package online.ronikier.todo.domain;

import lombok.*;
import online.ronikier.todo.templete.SuperEntity;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import javax.validation.constraints.Size;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@ToString
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@NodeEntity
public class Person extends SuperEntity {

    @Relationship(type = "KNOWS")
    protected Set<Person> knownPersons;

    @NonNull
    @Size(min = 6, max = 10)
    private String username;

    public void knows(Person person) {
        if (getKnownPersons() == null) {
            setKnownPersons(new HashSet<>());
        }
        getKnownPersons().add(person);
    }

    @Override
    public String toString() {
        return "'" + this.username + "' requires: " +
                Optional.ofNullable(this.getKnownPersons()).orElse(Collections.emptySet())
                        .stream()
                        .map(Person::getUsername)
                        .collect(Collectors.toList());
    }

    public Set<Person> getKnownPersons() {
        if (knownPersons == null) {
            setKnownPersons(new HashSet<>());
        }
        return knownPersons;
    }

}
