package online.ronikier.todo.templete;

import lombok.Data;
//import org.neo4j.ogm.annotation.GeneratedValue;
//import org.neo4j.ogm.annotation.Id;

import javax.persistence.*;

@Data
public abstract class SuperEntity {
    @Id
    @GeneratedValue
    protected Long id;
}
