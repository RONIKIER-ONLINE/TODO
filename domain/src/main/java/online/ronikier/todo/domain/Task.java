package online.ronikier.todo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import online.ronikier.todo.domain.dictionary.CostUnit;
import online.ronikier.todo.domain.dictionary.TaskState;
import online.ronikier.todo.domain.dictionary.TaskStatus;
import online.ronikier.todo.domain.dictionary.TaskType;
import online.ronikier.todo.library.Utilities;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

////@ToString // 500 java.lang.StackOverflowError: null - requiredTasks processing ???
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@NodeEntity

@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true, includeFieldNames = false)
@Data
@Entity(name="TASK")

public class Task { //extends SuperEntity {

    @Id
    @GeneratedValue
    protected Long id;

//    @Relationship(type = "REQUIRES")
    @ManyToMany
    protected List<Task> requiredTasks;
//    @Relationship(type = "IS DONE BY")
    @ManyToOne
    @JoinColumn(name = "responsible_person_id")
    protected Person responsiblePerson;

    @ManyToOne
    @JoinColumn(name = "is_required_by_task_id")
    protected Task isRequiredBy;

    @ManyToMany
    protected List<Task> isRequiredByList;
    // @NonNull
    protected Boolean important;
    // @NonNull
    protected Boolean urgent;
    // @NonNull
    // @Past
    private Date created;
    // @NonNull
    private Date start;
    // @NonNull
    // @Future
    private Date due;
    // @NonNull
    // @Future
    private Date stop;
    // @NonNull
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

    @OneToMany
    protected List<File> files;

    public void requires(Task task) {
        getRequiredTasks().add(task);
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

//    @Override
//    public String toString() {
//        return "'" + this.name + "' requires: " +
//                Optional.ofNullable(this.getRequiredTasks()).orElse(Collections.emptyList())
//                        .stream()
//                        .map(Task::getName)
//                        .collect(Collectors.toList());
//    }

    public String getCreatedFormatted() {
        return Utilities.stringFromDate(created);
    }

    public String getStartFormatted() {
        return Utilities.stringFromDate(start);
    }

    public String getDueFormatted() {
        return Utilities.stringFromDate(due);
    }

    public String getDescriptionHTML() {
        return (description != null)
                ? description.replace("\r","<BR/>")
                : description;
    }

}
