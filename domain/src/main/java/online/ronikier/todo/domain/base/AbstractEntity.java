package online.ronikier.todo.domain.base;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;

public abstract class AbstractEntity {
    @Id
    @GeneratedValue
    protected Long id;
}
