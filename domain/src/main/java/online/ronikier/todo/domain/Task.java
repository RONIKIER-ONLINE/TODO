package online.ronikier.todo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.*;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.*;
import java.util.stream.Collectors;

@NodeEntity
@Data
@AllArgsConstructor
public class Task extends Entity {

    @NotNull
    private Boolean important;

    @NotNull
    private Boolean urgent;

    @NotNull
    @Past
    private Date created;

    @NotNull
    @Future
    private Date due;

    @NotNull
    @NotEmpty
    @Size(max=20)
    private String name;

    @NotNull
    @Size(max=200)
    private String description;

    /**
     * Neo4j doesn't REALLY have bi-directional relationships. It just means when querying
     * to ignore the direction of the relationship.
     * https://dzone.com/articles/modelling-data-neo4j
     */
    @Relationship(type = "SUBTASK", direction = Relationship.UNDIRECTED)
    public Set<Task> subtasks;

    public void requires(Task task) {
        if (subtasks == null) {
            subtasks = new HashSet<>();
        }
        subtasks.add(task);
    }

    public String toString() {
        return "'" + this.name + "' subtasks: " + Optional.ofNullable(this.subtasks).orElse(Collections.emptySet()).stream().map(Task::getName).collect(Collectors.toList());
    }

    private Task() {
        // Empty constructor required as of Neo4j API 2.0.5
    };

}
