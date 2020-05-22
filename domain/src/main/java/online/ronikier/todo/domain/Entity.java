package online.ronikier.todo.domain;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;

public abstract class Entity {
    @Id
    @GeneratedValue
    protected Long id;
}
