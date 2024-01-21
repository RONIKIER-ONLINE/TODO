package online.ronikier.todo.domain.dictionary.utility;

import lombok.extern.slf4j.Slf4j;
import online.ronikier.todo.domain.dictionary.TaskState;
import online.ronikier.todo.domain.dictionary.TaskStatus;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.stream.Collectors;

@Slf4j
@Configuration
public class LegendsRepository {

    public static String taskStatusLegend() {
        return Arrays.stream(TaskStatus.values()).map(taskStatus -> taskStatus.getLabel() + " " + taskStatus)
                .collect(Collectors.joining("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"));
    }

    public static String taskStateLegeng() {
        return Arrays.stream(TaskState.values()).map(taskState -> taskState.getLabel() + " " + taskState)
                .collect(Collectors.joining("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"));
    }

}