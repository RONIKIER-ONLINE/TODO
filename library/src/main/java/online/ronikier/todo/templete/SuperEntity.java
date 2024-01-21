package online.ronikier.todo.templete;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
public abstract class SuperEntity {
    @Id
    @GeneratedValue
    protected Long id;
}
