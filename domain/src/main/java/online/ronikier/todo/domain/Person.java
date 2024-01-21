package online.ronikier.todo.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Size;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

//@ToString
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@NodeEntity


@NoArgsConstructor
//@ToString(onlyExplicitlyIncluded = true, includeFieldNames = false)
@Data
@Entity(name="PERSON")

public class Person {

    @Id
    @GeneratedValue
    private Long id;

    //@Relationship(type = "KNOWS")
    @ManyToMany
    private List<Person> knownPersons;

    @NonNull
    @Size(min = 6, max = 10)
    private String username;

    public void knows(Person person) {
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
        return knownPersons;
    }

}
