package online.ronikier.todo.domain.special;

import lombok.Data;
import lombok.NoArgsConstructor;
import online.ronikier.todo.domain.Task;
import org.neo4j.ogm.annotation.NodeEntity;

@Data
@NoArgsConstructor
@NodeEntity
public class ImportantTask extends Task {
}
