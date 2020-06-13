package online.ronikier.todo.templete;

import lombok.Data;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;

@Data
public abstract class SuperEntity {
    @Id
    @GeneratedValue
    protected Long id;
}
