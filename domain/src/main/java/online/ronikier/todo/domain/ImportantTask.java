package online.ronikier.todo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.neo4j.ogm.annotation.NodeEntity;

@Data
//@NoArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@NodeEntity
public class ImportantTask extends Task {
}
