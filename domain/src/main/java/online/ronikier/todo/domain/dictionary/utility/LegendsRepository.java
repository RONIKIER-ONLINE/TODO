package online.ronikier.todo.domain.dictionary.utility;

import online.ronikier.todo.domain.dictionary.TaskState;
import online.ronikier.todo.domain.dictionary.TaskStatus;

import java.util.Arrays;
import java.util.stream.Collectors;

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