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
    private Set<Task> requiredTasks;
    @NonNull
    private Boolean important;
    @NonNull
    private Boolean urgent;
//    @NonNull
//    @Past
    private Date created;
//    @NonNull
    private Date start;
//    @NonNull
//    @Future
    private Date due;
    @NonNull
    @Size(max = 30)
    private String name;
    @NonNull
    @Size(max = 200)
    private String description;

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
