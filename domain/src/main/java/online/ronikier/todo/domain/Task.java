package online.ronikier.todo.domain;

import lombok.*;
import online.ronikier.todo.domain.dictionary.CostUnit;
import online.ronikier.todo.domain.dictionary.TaskState;
import online.ronikier.todo.domain.dictionary.TaskStatus;
import online.ronikier.todo.domain.dictionary.TaskType;
import online.ronikier.todo.domain.extension.NewTask;
import online.ronikier.todo.library.Utilities;
import online.ronikier.todo.templete.SuperEntity;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import javax.validation.constraints.Size;
import java.util.*;
import java.util.stream.Collectors;

//@ToString // 500 java.lang.StackOverflowError: null - requiredTasks processing ???
@Data
@NoArgsConstructor
//@RequiredArgsConstructor
@AllArgsConstructor
@NodeEntity
public class Task extends SuperEntity {

    @Relationship(type = "REQUIRES")
    protected List<Task> requiredTasks;
    @Relationship(type = "IS DONE BY")
    protected Person responsiblePerson;
//    @NonNull
    protected Boolean important;
//    @NonNull
    protected Boolean urgent;
//    @NonNull
//    @Past
private Date created;
//    @NonNull
private Date start;
//    @NonNull
//    @Future
private Date due;
//    @NonNull
    @Size(max = 30)
    protected String name;
    //@NonNull
    @Size(max = 200)
    protected String description;

    protected Double costValue;

    protected CostUnit costUnit;

    protected TaskState taskState;

    protected TaskType taskType;

    protected TaskStatus taskStatus;

    public void requires(Task task) { getRequiredTasks().add(task);
    }

    public void isDoneBy(Person person) {
        setResponsiblePerson(person);
    }

    public List<Task> getRequiredTasks() {
        if (requiredTasks == null) {
            setRequiredTasks(new ArrayList<>());
        }
        return requiredTasks;
    }

    @Override
    public String toString() {
        return "'" + this.name + "' requires: " +
                Optional.ofNullable(this.getRequiredTasks()).orElse(Collections.emptyList())
                        .stream()
                        .map(Task::getName)
                        .collect(Collectors.toList());
    }

    public String getCreatedFormatted() {
        return Utilities.stringFromDate(created);
    }

    public String getStartFormatted() {
        return Utilities.stringFromDate(start);
    }

    public String getDueFormatted() {
        return Utilities.stringFromDate(due);
    }
}
