package online.ronikier.todo.interfaces.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import online.ronikier.todo.Messages;
import online.ronikier.todo.domain.ImportantTask;
import online.ronikier.todo.domain.Task;
import online.ronikier.todo.domain.exception.DataException;
import online.ronikier.todo.domain.forms.TaskForm;
import online.ronikier.todo.infrastructure.repository.TaskRepository;
import online.ronikier.todo.interfaces.base.AbstractController;
import online.ronikier.todo.interfaces.mappers.TaskMapper;
import online.ronikier.todo.library.Parameters;
import online.ronikier.todo.library.Utilities;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.*;

/**
 *
 */
@Slf4j
@Controller
@PropertySource("classpath:controller.properties")
@RequiredArgsConstructor
public class TaskController extends AbstractController {

    @Autowired
    private Set<Task> dafaultTasks;

    @Autowired
    private Task devTask;

    private final TaskRepository taskRepository;

    private final TaskMapper taskMapper;

    @Value("${task.completion.time.days:7}")
    private Integer taskCompletionTimeDays;

    /**
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/" + Parameters.WEB_CONTROLLER_TASK).setViewName(Parameters.WEB_CONTROLLER_TASK);
        registry.addViewController("/" + Parameters.WEB_CONTROLLER_TASK + Parameters.WEB_CONTROLLER_OPERATION_DELETE).setViewName(Parameters.WEB_CONTROLLER_TASK);
    }

    @PostMapping(Parameters.DEV_WEB_CONTROLLER_PATH)
    public String devPost(@ModelAttribute Task task, Model model) {
        model.addAttribute("task", task);
        return "dev";

    }

    @GetMapping(Parameters.DEV_WEB_CONTROLLER_PATH)
    public String devGet(Model model) {
        model.addAttribute("task", devTask);
        return "dev";
    }


    /**
     * @param taskForm
     * @param model
     * @return
     */
    @GetMapping(Parameters.WEB_CONTROLLER_TASK)
    public String task(TaskForm taskForm, Model model) {
        initializeForm(taskForm, model);
        return Parameters.WEB_CONTROLLER_TASK;
    }

    /**
     * @param taskId
     * @param taskForm
     * @param model
     * @return
     */
    @GetMapping(Parameters.WEB_CONTROLLER_TASK + "/" + "{" + Parameters.WEB_CONTROLLER_PARAMETER_TASK_ID + "}")
    public String taskShow(@PathVariable(name = Parameters.WEB_CONTROLLER_PARAMETER_TASK_ID, required = false) Long taskId, TaskForm taskForm, Model model) {

        Optional<Task> optionalTask = taskRepository.findById(taskId);

        if (!optionalTask.isPresent()) {
            log.info(Messages.REPOSITORY_TASK_NOT_FOUND + Messages.SEPARATOR + taskId);
            return Parameters.WEB_CONTROLLER_TASK;
        }

        Task selectedTask = optionalTask.get();

        initializeForm(taskForm, model);

        updateForm(taskForm, selectedTask);

        model.addAttribute("tasksRequiredTasks", getRequiredTaskList(selectedTask.getId()));

        refreshForm(taskForm, model);

        return Parameters.WEB_CONTROLLER_TASK;
    }

    /**
     * @param taskForm
     * @param bindingResult
     * @param model
     * @return
     */
    @PostMapping(Parameters.WEB_CONTROLLER_TASK)
    public String taskProcess(@Valid TaskForm taskForm, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            log.error(Messages.ERROR_TASK_ADD);
            bindingResult.getAllErrors().forEach(error -> log.error(Messages.SEPARATOR + error.toString()));
            initializeForm(taskForm, model);
            return Parameters.WEB_CONTROLLER_TASK;
        }

        try {

            Task processedTask = taskRepository.findByName(taskForm.getName());

            if (processedTask != null) {
                log.info((Messages.INFO_TASK_EXISTS));
                log.debug(processedTask.toString());
            } else {
                processedTask = initializeTask(taskForm);
            }

            saveTask(taskForm, processedTask);

            log.info(Messages.INFO_TASK_CREATED);
            log.debug(processedTask.toString());

        } catch (Exception e) {
            log.error(Messages.EXCEPTION_TASK_CREATION + Messages.SEPARATOR + e.getMessage());
        }

        refreshForm(taskForm, model);

        return Parameters.WEB_CONTROLLER_TASK;

    }

    @Transactional
    private void saveTask(TaskForm taskForm, Task task) {

        log.info(Messages.INFO_TASK_MODIFIED);

        taskMapper.form2Domain(taskForm, task);
        Optional<Task> requiredByTaskOptional = taskRepository.findById(Long.valueOf(taskForm.getRequiredByTaskId()));

        if (!requiredByTaskOptional.isPresent()) {
            log.error(Messages.ERROR_TASK_DOES_NOT_EXIST);
        } else {
            Task requiredByTask = requiredByTaskOptional.get();
            requiredByTask.requires(task);
            taskRepository.save(requiredByTask);
        }
        taskRepository.save(task);

        log.info(Messages.INFO_TASK_MODIFIED);
        log.debug(task.toString());
    }


    /**
     * @param taskId
     * @param taskForm
     * @param model
     * @return
     */
    @GetMapping(Parameters.WEB_CONTROLLER_TASK + Parameters.WEB_CONTROLLER_OPERATION_DELETE + "/" + "{" + Parameters.WEB_CONTROLLER_PARAMETER_TASK_ID + "}")
    public String taskDelete(@PathVariable(name = "taskId", required = false) Long taskId, TaskForm taskForm, Model model) {
        log.info(Messages.INFO_TASK_DELETING);
        taskRepository.deleteById(taskId);
        initializeForm(taskForm, model);
        return Parameters.WEB_CONTROLLER_TASK;
    }

    /**
     * @param taskForm
     * @return
     * @throws ParseException
     */
    private Task initializeTask(TaskForm taskForm) throws ParseException {


        //TODO: Implement system common tasks
        //return new Task(dafaultTasks,

        return new Task(getMaintanceTasks(taskForm.getName()),
                taskForm.getImportant(),
                taskForm.getUrgent(),
                Utilities.dateFromString(taskForm.getCreated()),
                Utilities.dateFromString(taskForm.getStart()),
                Utilities.dateFromString(taskForm.getDue()),
                taskForm.getName(),
                taskForm.getDescription());
    }

    private Set<Task> getMaintanceTasks(String taskName) {
        if (Parameters.SYSTEM_SKIP_MAINTENANCE_TASKS) {
            log.info(Messages.INFO_SKIPPING_MAINTENANCE_TASKS);
            return new HashSet<>();
        }
        //TODO: Implement individual task level tasks
        ImportantTask maintanceTaskA = new ImportantTask();
        //ImportantTask maintanceTaskA = new ImportantTask(null,true, true, Utilities.dateCurrent(), Utilities.dateCurrent(), Utilities.dateFuture(1), taskName + " A Maintance", "Maintance task A for " + taskName);
        //ImportantTask maintanceTaskB = new ImportantTask(null,true, true, Utilities.dateCurrent(), Utilities.dateCurrent(), Utilities.dateFuture(1), taskName + " B Maintance", "Maintance task B for " + taskName);
        List<Task> maintanceTasks = Arrays.asList(maintanceTaskA);
        return new HashSet<>(maintanceTasks);
    }

    /**
     * @param taskForm
     * @param model
     */
    private void initializeForm(TaskForm taskForm, Model model) {

        taskForm.setCreated(Utilities.stringFromDate(Utilities.dateCurrent()));
        taskForm.setStart(Utilities.stringFromDate(Utilities.dateCurrent()));
        taskForm.setDue(Utilities.stringFromDate(Utilities.dateFuture(taskCompletionTimeDays)));
        refreshForm(taskForm,model);

    }

    private void refreshForm(TaskForm taskForm, Model model) {

        model.addAttribute("taskList", getTaskList());
        taskForm.setTasks(getTaskList());
        model.addAttribute("taskCount", taskRepository.count());
    }

    /**
     * @param targetTaskForm
     * @param sourceTask
     */
    private void updateForm(TaskForm targetTaskForm, Task sourceTask) {

        // TODO: Date convertion - See TaskMapper - TaskMapper.INSTANCE.domain2Form(task, taskForm);

        targetTaskForm.setName(sourceTask.getName());
        targetTaskForm.setDescription(sourceTask.getDescription());
        targetTaskForm.setUrgent(sourceTask.getUrgent());
        targetTaskForm.setImportant(sourceTask.getImportant());
        targetTaskForm.setCreated(Utilities.stringFromDate(sourceTask.getCreated()));
        targetTaskForm.setStart(Utilities.stringFromDate(sourceTask.getStart()));
        targetTaskForm.setDue(Utilities.stringFromDate(sourceTask.getDue()));

    }

    private Iterable<Task> getRequiredTaskList(Long taskId) {
        log.error(Messages.DEV_IMLEMENT_ME + Messages.SEPARATOR + "Required task filtering");
        if (taskId == null) return null;
        return taskRepository.findById(taskId).get().getRequiredTasks();
    }

    private Iterable<Task> getTaskList() {
        log.error(Messages.DEV_IMLEMENT_ME + Messages.SEPARATOR + "Form task filtering");
        return taskRepository.findAll();
    }

}
