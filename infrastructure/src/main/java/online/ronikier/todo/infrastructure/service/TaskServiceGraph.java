package online.ronikier.todo.infrastructure.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import online.ronikier.todo.Messages;
import online.ronikier.todo.domain.Task;
import online.ronikier.todo.domain.dictionary.StateTask;
import online.ronikier.todo.domain.dictionary.TypeTask;
import online.ronikier.todo.infrastructure.repository.TaskRepository;
import online.ronikier.todo.library.Utilities;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 *
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TaskServiceGraph implements TaskService {

    private final TaskRepository taskRepository;

    public String kill() {
        return "Lou kills";
    }

    @Cacheable("TASKS_BY_ID")
    @Override
    public Optional<Task> findTaskById(Long taskId) {
        log.debug(Messages.DEBUG_MESSAGE_PREFIX + Messages.SEPARATOR + "FINDING TASK " + taskId);
        Optional<Task> taskOptional = taskRepository.findById(taskId);
        if (taskOptional.isPresent()) return taskOptional;
        log.info((Messages.INFO_TASK_NOT_FOUND + Messages.SEPARATOR + taskId));
        return Optional.empty();
    }

    @Cacheable("TASKS_BY_NAME")
    @Override
    public Task findTaskByName(String taskName) {
        log.debug(Messages.DEBUG_MESSAGE_PREFIX + Messages.SEPARATOR + "FINDING TASK " + Utilities.wrapString(taskName));
        return taskRepository.findByName(taskName);
    }

    @Override
    public void saveTask(Task task) {
        log.debug(Messages.DEBUG_MESSAGE_PREFIX + Messages.SEPARATOR + "SAVING TASK " + Utilities.wrapString(task.toString()));
        taskRepository.save(task);
    }

    @Override
    public void deleteTaskById(Long taskId) {
        log.debug(Messages.DEBUG_MESSAGE_PREFIX + Messages.SEPARATOR + "BUSTIN' TASK " + taskId);
        taskRepository.deleteById(taskId);
        log.debug(Messages.DEBUG_MESSAGE_PREFIX + Messages.SEPARATOR + "TASK DELETED ...");

    }

    @Override
    public Long countTasks() {
        return taskRepository.count();
    }

    @Override
    public List<Task> filteredTasks(Task filterValues) {

        //TODO: Implement filtering
        return allTasks();
    }

    @Override
    public List<Task> allTasks() {

        TEST_DEV();

        Comparator<Task> taskRequiredTasksComparator = (Task tasksA, Task tasksB) -> {
            return tasksB.getRequiredTasks().size() - tasksA.getRequiredTasks().size();
        };

        // NOTE: sorting cannot be done parallel
        boolean PARAllEL = false;



        return StreamSupport.stream(taskRepository.findAll().spliterator(), false).sorted(taskRequiredTasksComparator).collect(Collectors.toList());
        // otherwise use parallel for efficiency

    }

    @Override
    public List<Task> getMaintanceTasks(String taskName) {
        //TODO: Implement individual task level tasks
        Task maintanceTaskA = new Task(null, null, true, true, Utilities.dateCurrent(), Utilities.dateCurrent(), Utilities.dateFuture(1), taskName + " A Maintance", "Maintance task A for " + taskName, StateTask.INITIALIZED ,TypeTask.GENERAL);
        Task maintanceTaskB = new Task(null, null, true, true, Utilities.dateCurrent(), Utilities.dateCurrent(), Utilities.dateFuture(1), taskName + " B Maintance", "Maintance task B for " + taskName, StateTask.INITIALIZED ,TypeTask.GENERAL);
        return Arrays.asList(maintanceTaskA, maintanceTaskB);
    }

    @Override
    public List<Task> tasksRequiredTasks(Long taskId) {

        Optional<Task> tasksRequiredTasks = findTaskById(taskId);
        if (tasksRequiredTasks.isPresent()) {
            return tasksRequiredTasks.get().getRequiredTasks();
        }
        return null;
    }


    private void logMessage(String message) {
        log.info(message);
    }


    private void TEST_DEV() {

        Iterable<Task> tasks = taskRepository.findAll();

        Stream.of(tasks).forEach(task -> log.info(task.toString()));
        Stream.of(tasks).forEach(task -> log.info(task.toString()));


        Comparator<Task> taskComparator = (Task tasksA, Task tasksB) -> {
            return tasksB.getRequiredTasks().size() - tasksA.getRequiredTasks().size();
        };


        log.info("=========================================================");
        log.info("UNSORTER ================================================");
        log.info("=========================================================");

        StreamSupport.stream(tasks.spliterator(), true).forEach(task -> log.info(task.toString()));
        log.info("=========================================================");
        log.info("SORTED ==================================================");
        log.info("=========================================================");

        StreamSupport.stream(tasks.spliterator(), false).sorted(taskComparator).forEach(task -> log.info(task.toString()));

        if(true) return;



        Stream.of(taskRepository.findAll()).sorted().forEach(System.out::println);

        LongStream.range(1,10).forEach(l -> System.out.println(l));

        LongStream.range(1,10).forEach(System.out::println);

        System.out.print(LongStream.range(1,10).sum());

        Random randomNumber = new Random(100);

        System.out.print(LongStream
                .range(
                randomNumber.nextInt(),
                randomNumber.nextInt())
                .summaryStatistics());

        Arrays.stream(new int[] {1,2,3,4,5})
                .map(x -> x * x)
                .average()
                .ifPresent(System.out::println);

        //System.out.print(

        Stream.of(
                new Task(null,null,true, true, Utilities.dateCurrent(), Utilities.dateCurrent(), Utilities.dateFuture(1),"Task A1","Opisik", StateTask.INITIALIZED ,TypeTask.GENERAL),
                new Task(null,null,true, true, Utilities.dateCurrent(), Utilities.dateCurrent(), Utilities.dateFuture(1),"Task B2","Opisik", StateTask.INITIALIZED ,TypeTask.GENERAL),
                new Task(null,null,true, true, Utilities.dateCurrent(), Utilities.dateCurrent(), Utilities.dateFuture(1),"Task C3","Opisik", StateTask.INITIALIZED ,TypeTask.GENERAL),
                new Task(null,null,true, true, Utilities.dateCurrent(), Utilities.dateCurrent(), Utilities.dateFuture(1),"Task A4","Opisik", StateTask.INITIALIZED ,TypeTask.GENERAL),
                new Task(null,null,true, true, Utilities.dateCurrent(), Utilities.dateCurrent(), Utilities.dateFuture(1),"Task C5","Opisik", StateTask.INITIALIZED ,TypeTask.GENERAL),
                new Task(null,null,true, true, Utilities.dateCurrent(), Utilities.dateCurrent(), Utilities.dateFuture(1),"Task C5","Opisik", StateTask.INITIALIZED ,TypeTask.GENERAL),
                new Task(null,null,true, true, Utilities.dateCurrent(), Utilities.dateCurrent(), Utilities.dateFuture(1),"Task C6","Opisik", StateTask.INITIALIZED ,TypeTask.GENERAL)
        )
                //.count();

                .skip(1)
                .filter(task -> task.getName().contains("A"))
                .map(this::chujTask)

                //.forEach(System.out::println);

                .collect(Collectors.toList());

        //.reduce((task, task2) -> new Task(task.getName() + " " + task2.getName())).orElse(new Task("X"));

        try {
            Stream<String> lines = Files.lines(Paths.get("c:\\magazyn\\git\\TODO\\pom.xml"));
            lines.filter(line -> line.contains("version")).forEach(l -> System.out.println(l));
            lines.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public Task chujTask(online.ronikier.todo.domain.Task task) {
        task.setName("chuj");
        return task;
    }

}
