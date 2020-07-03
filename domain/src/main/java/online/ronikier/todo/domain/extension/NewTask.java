package online.ronikier.todo.domain.extension;

import lombok.Data;
import online.ronikier.todo.domain.Task;
import org.neo4j.ogm.annotation.NodeEntity;

@Data
@NodeEntity
@Deprecated
// TODO: This will have to make online.ronikier.todo.domain.Task abstract
// TODO: TaskStatus obsolete ???
public class NewTask extends Task {

}
