package online.ronikier.todo.interfaces.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import online.ronikier.todo.domain.Task;
import online.ronikier.todo.infrastructure.TaskRepository;
import online.ronikier.todo.interfaces.base.AbstractController;
import online.ronikier.todo.Messages;
import online.ronikier.todo.library.Utilities;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.Optional;

@Slf4j
@Controller
@AllArgsConstructor
public class TaskController extends AbstractController  {

    private final TaskRepository taskRepository;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/task").setViewName("task");
    }

    @GetMapping("/task")
    public String taskAddShow(TaskForm taskForm, Model model) {

        initializeForm(taskForm, model);

        return "task";
    }

    @GetMapping("/task/{taskId}")
    public String taskAddShow(@PathVariable(name = "taskId",required = false) Long taskId, TaskForm taskForm, Model model) {

        initializeForm(taskForm, model);

        Optional<Task> selectedTask = taskRepository.findById(taskId);
        Task task = selectedTask.get();

        if (task == null) {
            log.info(Messages.REPOSITORY_TASK_NOT_FOUND + Messages.SEPARATOR + taskId);

            initializeForm(taskForm, model);

            return "task";
        }

        initializeForm(taskForm, task);

        return "task";

    }

    private void initializeForm(TaskForm taskForm, Task task) {
        taskForm.setName(task.getName());
        taskForm.setDescription(task.getDescription());
        taskForm.setUrgent(task.getUrgent());
        taskForm.setImportant(task.getImportant());
        taskForm.setCreated(Utilities.stringFromDate(task.getCreated()));
        taskForm.setStart(Utilities.stringFromDate(task.getStart()));
        taskForm.setDue(Utilities.stringFromDate(task.getDue()));
    }

    @PostMapping("/task")
    public String taskAdd(@Valid TaskForm taskForm, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            log.error(Messages.ERROR_TASK_ADD);
            bindingResult.getAllErrors().forEach(error -> log.error(Messages.SEPARATOR + error.toString()));

            initializeForm(taskForm, model);

            return "task";
        }

        try {
            Task processedTask = taskRepository.findByName(taskForm.getName());
            if (processedTask != null) {
                log.info(Messages.INFO_TASK_EXIST);
                updateTask(processedTask,taskForm);
                taskRepository.save(processedTask);

                initializeForm(taskForm, model);

                return "task";
            }

            Task newTask = initializeTask(taskForm);

            taskRepository.save(newTask);

            //Task millionDollarTask = taskRepository.findByName("Be Rich");

            Task uberTask = taskRepository.findById(Long.valueOf(taskForm.getUberTaskId())).get();

            uberTask.requires(newTask);

            taskRepository.save(uberTask);

            log.info(Messages.INFO_TASK_CREATED);



        } catch (Exception e) {
            log.error(Messages.EXCEPTION_TASK_CREATION + Messages.SEPARATOR + e.getMessage());
        }

        initializeForm(taskForm, model);

        return "task";

    }

    @PostMapping("/task_save")
    public String taskSave(@Valid TaskForm taskForm, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            log.error(Messages.ERROR_TASK_ADD);
            bindingResult.getAllErrors().forEach(error -> log.error(Messages.SEPARATOR + error.toString()));

            initializeForm(taskForm, model);

            return "task";
        }

        try {

            Task modifiedTask = taskRepository.findByName(taskForm.getName());

            if (modifiedTask == null) {
                log.error(Messages.REPOSITORY_TASK_NOT_FOUND + Messages.SEPARATOR + modifiedTask);

                initializeForm(taskForm, model);

                return "task";
            }

            updateTask(modifiedTask, taskForm);

            taskRepository.save(modifiedTask);

//            Task millionDollarTask = taskRepository.findByName("Be Rich");
//
//            millionDollarTask.requires(modifiedTask);
//
//            taskRepository.save(millionDollarTask);

            log.info(Messages.INFO_TASK_MODIFIED);

        } catch (Exception e) {
            log.error(Messages.EXCEPTION_TASK_CREATION + Messages.SEPARATOR + e.getMessage());
        }

        initializeForm(taskForm, model);

        return "task";

    }

    private void updateTask(Task task, TaskForm taskForm) throws ParseException {
        task.setName(taskForm.getName());
        task.setDescription(taskForm.getDescription());
        task.setUrgent(taskForm.getUrgent());
        task.setImportant(taskForm.getImportant());
        task.setCreated(Utilities.dateFromString(taskForm.getCreated()));
        task.setStart(Utilities.dateFromString(taskForm.getStart()));
        task.setDue(Utilities.dateFromString(taskForm.getDue()));
    }


    private void initializeForm(@Valid TaskForm taskForm, Model model) {
        model.addAttribute("allTasks",taskRepository.findAll());
        taskForm.setUberTasks(taskRepository.findAll());
        model.addAttribute("taskCount",taskRepository.count());
        taskForm.setCreated(Utilities.stringFromDate(Utilities.dateCurrent()));
        taskForm.setStart(Utilities.stringFromDate(Utilities.dateCurrent()));
        taskForm.setDue(Utilities.stringFromDate(Utilities.dateFuture(7)));
    }

    private Task initializeTask(@Valid TaskForm taskForm) throws ParseException {
        return new Task(
                taskForm.getImportant(),
                taskForm.getUrgent(),
                Utilities.dateFromString(taskForm.getCreated()),
                Utilities.dateFromString(taskForm.getStart()),
                Utilities.dateFromString(taskForm.getDue()),
                taskForm.getName(),
                taskForm.getDescription());
    }

}
