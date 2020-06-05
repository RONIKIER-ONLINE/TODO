package online.ronikier.todo.domain;

import lombok.*;
import online.ronikier.todo.domain.base.AbstractEntity;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import javax.validation.constraints.Size;
import java.util.*;
import java.util.stream.Collectors;

@Data
//@NoArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@NodeEntity
public class Task extends AbstractEntity {

    @Relationship(type = "REQUIRES")
    protected Set<Task> requiredTasks;
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

    public void requires(Task task) {
        if (getRequiredTasks() == null) {
            setRequiredTasks(new HashSet<>());
        }
        getRequiredTasks().add(task);
    }


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

    public void setRequiredTasks(Set<Task> requiredTasks) {
        this.requiredTasks = requiredTasks;
    }
}
