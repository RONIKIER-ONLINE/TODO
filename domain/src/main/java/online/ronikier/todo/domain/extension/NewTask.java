package online.ronikier.todo.domain.extension;

import lombok.Data;
import online.ronikier.todo.domain.Task;
import org.neo4j.ogm.annotation.NodeEntity;

/**      * @deprecated (when, why, refactoring advice...)      */
@Data
@NodeEntity
@Deprecated(forRemoval = true)
// This will have to make online.ronikier.todo.domain.Task abstract
// TaskStatus obsolete ???
public class NewTask extends Task {

}
