package online.ronikier.todo.domain;

import lombok.ToString;
import online.ronikier.todo.domain.dictionary.FileType;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor
//@ToString(onlyExplicitlyIncluded = true, includeFieldNames = false)
@Data
@Entity(name="FILE")
@ToString
public class File {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String contentType;

    private Long size;

    private Date uploadDate;

    @ManyToOne
    private Task task;

    private FileType fileType;

}
