package online.ronikier.todo.domain.extension;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import online.ronikier.todo.domain.Task;
import org.neo4j.ogm.annotation.NodeEntity;

@Slf4j
@Data
@NodeEntity
// TODO: TaskStatus obsolete ???
public class DelayedTask extends Task {
    private void doSomethingFast() {
        log.warn("AAAAAAAAA !!!");
        throw new UnsupportedOperationException();
    }
}
