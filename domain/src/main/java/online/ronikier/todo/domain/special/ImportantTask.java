package online.ronikier.todo.domain.special;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import online.ronikier.todo.domain.Person;
import online.ronikier.todo.domain.Task;
import online.ronikier.todo.domain.dictionary.CostUnit;
import online.ronikier.todo.domain.dictionary.TaskState;
import online.ronikier.todo.domain.dictionary.TaskStatus;
import online.ronikier.todo.domain.dictionary.TaskType;
//import org.neo4j.ogm.annotation.NodeEntity;

import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Deprecated
// TODO:Do we need special tasks -> see the differently in graphs
//@NodeEntity
public class ImportantTask extends Task {
//    public ImportantTask(List<Task> requiredTasks, Person responsiblePerson, Task isRequiredBy, List<Task> isRequiredByList, Boolean important, Boolean urgent, Date created, Date start, Date due, @Size(max = 30) String name, @Size(max = 200) String description, Double costValue, CostUnit costUnit, TaskState taskState, TaskType taskType, TaskStatus taskStatus) {
//        super(requiredTasks, responsiblePerson, isRequiredBy, isRequiredByList, important, urgent, created, start, due, name, description, costValue, costUnit, taskState, taskType, taskStatus);
//    }
}
