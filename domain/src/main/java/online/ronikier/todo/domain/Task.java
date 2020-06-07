package online.ronikier.todo.domain;

import lombok.*;
import online.ronikier.todo.templete.SuperEntity;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import javax.validation.constraints.Size;
import java.util.*;
import java.util.stream.Collectors;

//@ToString // 500 java.lang.StackOverflowError: null - requiredTasks processing ???
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@NodeEntity
public class Task extends SuperEntity {

    @Relationship(type = "REQUIRES")
    protected Set<Task> requiredTasks;
    @Relationship(type = "IS DONE BY")
    protected Set<Person> responsible;
    @NonNull
    protected Boolean important;
    @NonNull
    protected Boolean urgent;
//    @NonNull
//    @Past
    protected Date created;
//    @NonNull
    protected Date start;
//    @NonNull
//    @Future
    protected Date due;
    @NonNull
    @Size(max = 30)
    protected String name;
    //@NonNull
    @Size(max = 200)
    protected String description;

    protected Person person;

    public void requires(Task task) {
        if (getRequiredTasks() == null) {
            setRequiredTasks(new HashSet<>());
        }
        getRequiredTasks().add(task);
    }

    public void isDoneBy(Person person) {
        if (getResponsible() == null) {
            setResponsible(new HashSet<>());
        }
        getResponsible().add(person);
    }

    @Override
    public String toString() {
        return "'" + this.name + "' requires: " +
                Optional.ofNullable(this.getRequiredTasks()).orElse(Collections.emptySet())
                        .stream()
                        .map(Task::getName)
                        .collect(Collectors.toList());
    }

    public Set<Task> getRequiredTasks() {
        if (requiredTasks == null) {
            setRequiredTasks(new HashSet<>());
        }
        return requiredTasks;
    }

    public Set<Person> getResponsible() {
        if (responsible == null) {
            setResponsible(new HashSet<>());
        }
        return responsible;
    }

}
