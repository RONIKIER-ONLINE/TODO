package online.ronikier.todo.domain;

import lombok.*;
import online.ronikier.todo.domain.base.AbstractEntity;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import javax.validation.constraints.Future;
import javax.validation.constraints.Past;
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

    @Relationship(type = "REQUIRES", direction = Relationship.OUTGOING)
    public Set<Task> requiredTasks;
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
    @Size(max = 20)
    private String name;
    @NonNull
    @Size(max = 200)
    private String description;

    public void requires(Task task) {
        if (requiredTasks == null) {
            requiredTasks = new HashSet<>();
        }
        requiredTasks.add(task);
    }

    public String toString() {
        return "'" + this.name + "' requires: " +
                Optional.ofNullable(this.requiredTasks).orElse(Collections.emptySet())
                        .stream()
                        .map(Task::getName)
                        .collect(Collectors.toList());
    }

}
