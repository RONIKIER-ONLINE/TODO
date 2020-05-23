package online.ronikier.todo.domain;

import lombok.*;
import online.ronikier.todo.domain.base.AbstractEntity;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import javax.validation.constraints.*;
import java.util.*;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@NodeEntity
public class Task extends AbstractEntity {

    @NonNull
    private Boolean important;

    @NonNull
    private Boolean urgent;

    @NonNull
    @Past
    private Date created;

    @NonNull
    private Date start;

    @NonNull
    @Future
    private Date due;

    @NonNull
    @Size(max = 20)
    private String name;

    @NonNull
    @Size(max = 200)
    private String description;

    /**
     * Neo4j doesn't REALLY have bi-directional relationships. It just means when querying
     * to ignore the direction of the relationship.
     * https://dzone.com/articles/modelling-data-neo4j
     */
    @Relationship(type = "SUBTASK", direction = Relationship.INCOMING)
    public Set<Task> subTasks;

    public void requires(Task task) {
        if (subTasks == null) {
            subTasks = new HashSet<>();
        }
        subTasks.add(task);
    }

    public String toString() {
        return "'" + this.name + "' subtasks: " +
            Optional.ofNullable(this.subTasks).orElse(Collections.emptySet())
                .stream()
                .map(Task::getName)
                .collect(Collectors.toList());
    }

}
