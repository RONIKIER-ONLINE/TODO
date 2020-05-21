package online.ronikier.todo.domain;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@NodeEntity
@Data
public class Task {

    @Id @GeneratedValue private Long id;

    @NotNull
    @Size(min=1, max=20)
    private String name;

    @NotNull
    @Size(min=1, max=200)
    private String description;

    private Task() {
        // Empty constructor required as of Neo4j API 2.0.5
    };

    public Task(String name) {
        this.name = name;
    }

    /**
     * Neo4j doesn't REALLY have bi-directional relationships. It just means when querying
     * to ignore the direction of the relationship.
     * https://dzone.com/articles/modelling-data-neo4j
     */
    @Relationship(type = "SUBTASK", direction = Relationship.UNDIRECTED)
    public Set<Task> subtasks;

    public void requiresCompletion(Task task) {
        if (subtasks == null) {
            subtasks = new HashSet<>();
        }
        subtasks.add(task);
    }

    public String toString() {

        return this.name + "'s subtasks => "
                + Optional.ofNullable(this.subtasks).orElse(
                Collections.emptySet()).stream()
                .map(Task::getName)
                .collect(Collectors.toList());
    }

}
