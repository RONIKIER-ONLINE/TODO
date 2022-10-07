package online.ronikier.todo.infrastructure.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import online.ronikier.todo.Messages;
import online.ronikier.todo.domain.Brain;
import online.ronikier.todo.domain.Task;
import online.ronikier.todo.domain.dictionary.*;
import online.ronikier.todo.domain.forms.TaskFilterForm;
import online.ronikier.todo.infrastructure.repository.TaskRepository;
import online.ronikier.todo.library.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
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

    private final Brain brain;

    private final TaskRepository taskRepository;

    @Override
    public boolean securityCheckOK() {
        return brain.getLoggedPerson() != null;
    }

    public void kill() {
        brain.setLoggedPerson(null);
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
    @CacheEvict
    public void saveTask(Task task) {
        log.debug(Messages.DEBUG_MESSAGE_PREFIX + Messages.SEPARATOR + "SAVING TASK " + Utilities.wrapString(task.toString()));
        taskRepository.save(task);
    }

    @Override
    @CacheEvict
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
    public List<Task> filteredTasks(TaskFilterForm taskFilterForm, SortOrder sortOrder) {

        if (taskFilterForm == null) return allTasks(sortOrder);

        Stream<Task> filteredTasksStream = allTasks(sortOrder).parallelStream();


        //TDOD: Refactor (but not too much ;)
        filteredTasksStream = filterSelected(taskFilterForm.getTaskType())
            ? filteredTasksStream.filter(task -> taskFilterForm.getTaskType().equals(task.getTaskType()))
            : filteredTasksStream;

        filteredTasksStream = filterSelected(taskFilterForm.getTaskState())
            ? filteredTasksStream.filter(task -> taskFilterForm.getTaskState().equals(task.getTaskState()))
            : filteredTasksStream;

        filteredTasksStream = filterSelected(taskFilterForm.getTaskStatus())
            ? filteredTasksStream.filter(task -> taskFilterForm.getTaskStatus().equals(task.getTaskStatus()))
            : filteredTasksStream;

        filteredTasksStream = filterFlag(taskFilterForm.getImportant())
            ? filteredTasksStream.filter(Task::getImportant)
            : filteredTasksStream;

        filteredTasksStream = filterFlag(taskFilterForm.getUrgent())
            ? filteredTasksStream.filter(Task::getUrgent)
            : filteredTasksStream;

        filteredTasksStream = filterSelected(taskFilterForm.getPhrase())
            ? filteredTasksStream.filter(task -> (
                task.getName().toUpperCase().contains(taskFilterForm.getPhrase().toUpperCase())
                ||
                task.getDescription().toUpperCase().contains(taskFilterForm.getPhrase().toUpperCase())))
            : filteredTasksStream;

        return filteredTasksStream.collect(Collectors.toList());

    }

    @Override
    @Cacheable("TASKS_SORTED")
    public List<Task> allTasks(SortOrder sortOrder) {

        Comparator<Task> taskComparator = Comparator.comparing(Task::getName);

        switch (sortOrder) {
            case NONE: return StreamSupport.stream(taskRepository.findAll().spliterator(), true).collect(Collectors.toList());
            case DEFAULT :
            case REQUIRED_TASKS : {
                taskComparator = (Task tasksA, Task tasksB) -> tasksB.getRequiredTasks().size() - tasksA.getRequiredTasks().size();
                break;
            }
            case NAME :
            default:
        }

        // NOTE: sorting cannot be done parallel
        boolean PARAllEL = false;

        return StreamSupport.stream(taskRepository.findAll().spliterator(), PARAllEL).sorted(taskComparator).collect(Collectors.toList());
        // otherwise use parallel for efficiency

    }

    @Override
    public List<Task> getMaintanceTasks() {
        //TODO: Implement individual task level tasks
        //TODO: One task set for alla ore task set each
        Task maintanceTaskA = new Task(null, null, true, true, Utilities.dateCurrent(), Utilities.dateCurrent(), Utilities.dateFuture(1), "A Maintance", "Maintance task A", 0d, CostUnit.SOLDIER, TaskState.NEW ,TaskType.GENERAL, TaskStatus.OK);
        Task maintanceTaskB = new Task(null, null, true, true, Utilities.dateCurrent(), Utilities.dateCurrent(), Utilities.dateFuture(1), "B Maintance", "Maintance task B", 0d, CostUnit.SOLDIER, TaskState.NEW ,TaskType.GENERAL, TaskStatus.OK);
        return Arrays.asList(maintanceTaskA, maintanceTaskB);
    }

    @Override
    @Cacheable("TASKS_REQUIRED")
    public List<Task> tasksRequiredTasks(Long taskId) {

        Optional<Task> tasksRequiredTasks = findTaskById(taskId);
        return tasksRequiredTasks.map(Task::getRequiredTasks).orElse(null);
    }

    private boolean filterSelected(Object filter) {
        return Optional.ofNullable(filter).isPresent();
    }

    private boolean filterFlag(Boolean flag) {
        if (filterSelected(flag)) return flag;
        return false;
    }

    @Deprecated(forRemoval = true)
    private void TEST_DEVX() {

        Iterable<Task> tasks = taskRepository.findAll();

        Stream.of(tasks).forEach(task -> log.info(task.toString()));
        Stream.of(tasks).forEach(task -> log.info(task.toString()));


        Comparator<Task> taskComparator = (Task tasksA, Task tasksB) -> tasksB.getRequiredTasks().size() - tasksA.getRequiredTasks().size();


        log.info("=========================================================");
        log.info("UNSORTER ================================================");
        log.info("=========================================================");

        StreamSupport.stream(tasks.spliterator(), true).forEach(task -> log.info(task.toString()));
        log.info("=========================================================");
        log.info("SORTED ==================================================");
        log.info("=========================================================");

        StreamSupport.stream(tasks.spliterator(), false).sorted(taskComparator).forEach(task -> log.info(task.toString()));

        if(true) return;



        Stream.of(taskRepository.findAll()).forEach(System.out::println);

        LongStream.range(1,10).forEach(System.out::println);

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
                new Task(null,null,true, true, Utilities.dateCurrent(), Utilities.dateCurrent(), Utilities.dateFuture(1),"Task A1","Opisik", 0d, CostUnit.SOLDIER, TaskState.NEW ,TaskType.GENERAL, TaskStatus.OK),
                new Task(null,null,true, true, Utilities.dateCurrent(), Utilities.dateCurrent(), Utilities.dateFuture(1),"Task B2","Opisik", 0d, CostUnit.SOLDIER, TaskState.NEW ,TaskType.GENERAL, TaskStatus.OK),
                new Task(null,null,true, true, Utilities.dateCurrent(), Utilities.dateCurrent(), Utilities.dateFuture(1),"Task C3","Opisik", 0d, CostUnit.SOLDIER, TaskState.NEW ,TaskType.GENERAL, TaskStatus.OK),
                new Task(null,null,true, true, Utilities.dateCurrent(), Utilities.dateCurrent(), Utilities.dateFuture(1),"Task A4","Opisik", 1000000d, CostUnit.PLN, TaskState.NEW ,TaskType.GENERAL, TaskStatus.OK),
                new Task(null,null,true, true, Utilities.dateCurrent(), Utilities.dateCurrent(), Utilities.dateFuture(1),"Task C5","Opisik", 0d, CostUnit.SOLDIER, TaskState.NEW ,TaskType.GENERAL, TaskStatus.OK),
                new Task(null,null,true, true, Utilities.dateCurrent(), Utilities.dateCurrent(), Utilities.dateFuture(1),"Task C5","Opisik", 7d, CostUnit.SOLDIER, TaskState.NEW ,TaskType.GENERAL, TaskStatus.OK),
                new Task(null,null,true, true, Utilities.dateCurrent(), Utilities.dateCurrent(), Utilities.dateFuture(1),"Task C6","Opisik", 7d, CostUnit.DAY, TaskState.NEW ,TaskType.GENERAL, TaskStatus.OK)
        )
                //.count();

                .skip(1)
                .filter(task -> task.getName().contains("A"))
                .map(this::transformTask)

                //.forEach(System.out::println);

                .collect(Collectors.toList());

        //.reduce((task, task2) -> new Task(task.getName() + " " + task2.getName())).orElse(new Task("X"));

        try {
            Stream<String> lines = Files.lines(Paths.get("c:\\magazyn\\git\\TODO\\pom.xml"));
            lines.filter(line -> line.contains("version")).forEach(System.out::println);
            lines.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public Task transformTask(online.ronikier.todo.domain.Task task) {
        task.setName(task.getName() + " has been transformed ...");
        return task;
    }



}
