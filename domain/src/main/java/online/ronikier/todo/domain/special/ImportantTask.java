package online.ronikier.todo.domain.special;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import online.ronikier.todo.domain.Task;
import org.neo4j.ogm.annotation.NodeEntity;

@ToString
@Data
@NoArgsConstructor
@NodeEntity
public class ImportantTask extends Task {
}
