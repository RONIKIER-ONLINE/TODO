package online.ronikier.todo.domain.base;

import lombok.Data;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;

@Data
public abstract class AbstractEntity {
    @Id
    @GeneratedValue
    protected Long id;
}
