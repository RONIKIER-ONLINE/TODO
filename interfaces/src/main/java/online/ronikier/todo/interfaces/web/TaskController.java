package online.ronikier.todo.interfaces.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import online.ronikier.todo.Messages;
import online.ronikier.todo.domain.Person;
import online.ronikier.todo.domain.Task;
import online.ronikier.todo.domain.dictionary.CostUnit;
import online.ronikier.todo.domain.dictionary.SortOrder;
import online.ronikier.todo.domain.dictionary.StateTask;
import online.ronikier.todo.domain.dictionary.TypeTask;
import online.ronikier.todo.domain.forms.TaskForm;
import online.ronikier.todo.infrastructure.service.PersonService;
import online.ronikier.todo.infrastructure.service.TaskService;
import online.ronikier.todo.interfaces.mappers.TaskMapper;
import online.ronikier.todo.library.Parameters;
import online.ronikier.todo.library.Utilities;
import online.ronikier.todo.templete.SuperController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
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
public class TaskController extends SuperController {

    @Deprecated
    private final Task devTask;

    private final Set<Task> dafaultTasks;

    private final TaskService taskService;

    private final TaskMapper taskMapper;

    private final PersonService personService;

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

    /**
     * @param taskForm
     * @param model
     * @return
     */
    @GetMapping(value = Parameters.WEB_CONTROLLER_TASK, produces = "text/html")
    public String getTask(TaskForm taskForm, Model model) {
        refreshForm(taskForm,model);
        return Parameters.WEB_CONTROLLER_TASK;
    }

    /**
     * @param taskId
     * @param taskForm
     * @param model
     * @return
     */
    @GetMapping(value = Parameters.WEB_CONTROLLER_TASK + "/" + "{" + Parameters.WEB_CONTROLLER_PARAMETER_TASK_ID + "}", produces = "text/html")
    public String getTaskById(@PathVariable(name = Parameters.WEB_CONTROLLER_PARAMETER_TASK_ID, required = false) Long taskId, TaskForm taskForm, Model model) {
        Optional<Task> optionalTask = taskService.findTaskById(taskId);
        if (!optionalTask.isPresent()) {
            log.info(Messages.REPOSITORY_TASK_NOT_FOUND + Messages.SEPARATOR + taskId);
            return Parameters.WEB_CONTROLLER_TASK;
        }
        Task selectedTask = optionalTask.get();
        //initializeForm(taskForm, model);
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
    public String postTask(@Valid TaskForm taskForm, BindingResult bindingResult, Model model) {
        if (!"filter".equals(taskForm.getAction())) {
            if (bindingResult.hasErrors()) {
                log.error(Messages.ERROR_TASK_ADD);
                bindingResult.getAllErrors().forEach(error -> log.error(Messages.SEPARATOR + error.toString()));
                initializeForm(taskForm, model);
                return Parameters.WEB_CONTROLLER_TASK;
            }
            try {
                Task processedTask = taskService.findTaskByName(taskForm.getName());
                if (processedTask != null) {
                    log.info((Messages.INFO_TASK_EXISTS));
                    processedTask.setStateTask(StateTask.MODIFIED);
                    log.debug(processedTask.toString());
                } else {
                    processedTask = initializeTask();
                    processedTask.setStateTask(StateTask.INITIALIZED);
                    processedTask.setTypeTask(TypeTask.GENERAL);
                }
                saveTask(taskForm, processedTask);
                log.info(Messages.INFO_TASK_CREATED);
                log.debug(processedTask.toString());
            } catch (Exception e) {
                log.error(Messages.EXCEPTION_TASK_CREATION + Messages.SEPARATOR + e.getMessage());
            }
        }
        refreshForm(taskForm, model);
        return Parameters.WEB_CONTROLLER_TASK;
    }

    /**
     * @param taskId
     * @param taskForm
     * @param model
     * @return
     */
    @GetMapping(value = Parameters.WEB_CONTROLLER_TASK + Parameters.WEB_CONTROLLER_OPERATION_DELETE + "/" + "{" + Parameters.WEB_CONTROLLER_PARAMETER_TASK_ID + "}", produces = "text/html")
    public String getTaskDelete(@PathVariable(name = "taskId", required = false) Long taskId, TaskForm taskForm, Model model) {
        log.info(Messages.INFO_TASK_DELETING);
        taskService.deleteTaskById(taskId);
        initializeForm(taskForm, model);
        model.addAttribute("tasksRequiredTasks", null);
        return Parameters.WEB_CONTROLLER_TASK;
    }

    /**
     *
     * @param taskForm
     * @param task
     */
    private void saveTask(TaskForm taskForm, Task task) {
        log.info(Messages.INFO_TASK_MODIFIED);
        taskMapper.form2Domain(taskForm, task);

        assignResponsible(taskForm, task);

        if (taskForm.getRequiredByTaskId() != null && !taskForm.getRequiredByTaskId().equals("none")) {
            Optional<Task> requiredByTaskOptional = taskService.findTaskById(Long.valueOf(taskForm.getRequiredByTaskId()));
            if (!requiredByTaskOptional.isPresent()) {
                log.error(Messages.ERROR_TASK_DOES_NOT_EXIST);
            } else {
                Task requiredByTask = requiredByTaskOptional.get();
                requiredByTask.requires(task);
                taskService.saveTask(requiredByTask);
            }
        } else {
            if (Parameters.SYSTEM_SAVE_NOT_REQUIRED) {
                taskService.saveTask(task);
            } else {
                log.info(Messages.INFO_SKIPPING_NOT_REQUIRED);
                return;
            }
        }

        taskForm.getTask().setTypeTask(task.getTypeTask());
        taskForm.getTask().setStateTask(task.getStateTask());

        log.info(Messages.INFO_TASK_MODIFIED);
        log.debug(task.toString());
    }

    /**
     *
     * @param taskForm
     * @param task
     */
    private void assignResponsible(TaskForm taskForm, Task task) {
        Optional<Person> responsibleOptional = personService.findPersonById(Long.valueOf(taskForm.getResponsiblePersonId()));
        if (responsibleOptional.isPresent()) {
            task.isDoneBy(responsibleOptional.get());
            log.debug(Messages.DEBUG_MESSAGE_PREFIX + Messages.SEPARATOR + task + Messages.SEPARATOR + responsibleOptional.get());
        }
    }


    /**
     *
     * @param taskForm
     * @return
     * @throws ParseException
     */
    private Task initializeTask() {

        //TODO: Implement system common tasks
        //return new Task(dafaultTasks,


        Task newTask = new Task(
                appendMaintanceTasks(),
                null,
                null,
                null,
                Utilities.dateCurrent(),
                null,       //Utilities.dateCurrent(),
                null,       //Utilities.dateFuture(taskCompletionTimeDays),
                null,
                null,
                null,    //Double.valueOf(0),
                null,     //CostUnit.SOLDIERS,
                null,    //StateTask.INITIALIZED,
                null     //TypeTask.GENERAL
        );

        return newTask;

    }

    /**
     *
     * @param taskName
     * @return
     */
    private List<Task> appendMaintanceTasks() {
        if (Parameters.SYSTEM_SKIP_MAINTENANCE_TASKS) {
            log.info(Messages.INFO_SKIPPING_MAINTENANCE_TASKS);
            return null;
        }
        return taskService.getMaintanceTasks();
    }

    /**
     *
     * @param taskId
     * @return
     */
    private Iterable<Task> getRequiredTaskList(Long taskId) {
        log.error(Messages.DEV_IMPLEMENT_ME + Messages.SEPARATOR + "Required task filtering");
        if (taskId == null) return null;
        return taskService.tasksRequiredTasks(taskId);
    }

    /**
     *
     * @return
     */
    private List<Task> getTaskList(SortOrder sortOrder) {
        return taskService.allTasks(sortOrder);
    }

    /**
     *
     * @return
     */
    private List<Task> getFilteredTaskList(Task taskFilter,SortOrder sortOrder) {
        return taskService.filteredTasks(taskFilter,sortOrder);
    }



    /**
     * @param taskForm
     * @param model
     */
    private void initializeForm(TaskForm taskForm, Model model) {
        taskForm.setTask(initializeTask());
        taskForm.setTaskFilter(initializeTask());
        taskForm.setShowTaskDetails(false);
        refreshForm(taskForm,model);
    }

    /**
     *
     * @param taskForm
     * @param model
     */
    private void refreshForm(TaskForm taskForm, Model model) {

        SortOrder taskListSortOrder = SortOrder.DEFAULT;

        if (taskForm.getShowTaskDetails() != null && taskForm.getShowTaskDetails()) {
            model.addAttribute("showTaskDetails", true);
        } else {
            taskListSortOrder = SortOrder.NAME;
        }
        model.addAttribute("taskList", getFilteredTaskList(taskForm.getTaskFilter(),taskListSortOrder));

        taskForm.setTasks(getTaskList(SortOrder.NAME));
        taskForm.setPersons(getPersonList());
        //taskForm.setTask(null);
        model.addAttribute("taskCount", taskService.countTasks());
        model.addAttribute("showFcknDialog", taskForm.getShowDialog());
    }

    private List<Person> getPersonList() {

        return personService.allPersons();
    }

    /**
     *
     * @param targetTaskForm
     * @param sourceTask
     */
    private void updateForm(TaskForm targetTaskForm, Task sourceTask) {

        taskMapper.domain2Form(sourceTask, targetTaskForm);

        //targetTaskForm.setTask(sourceTask);

//        targetTaskForm.setName(sourceTask.getName());
//        targetTaskForm.setDescription(sourceTask.getDescription());
//        targetTaskForm.setUrgent(sourceTask.getUrgent());
//        targetTaskForm.setImportant(sourceTask.getImportant());
//        targetTaskForm.setCreated(Utilities.stringFromDate(sourceTask.getCreated()));
//        targetTaskForm.setStart(Utilities.stringFromDate(sourceTask.getStart()));
//        targetTaskForm.setDue(Utilities.stringFromDate(sourceTask.getDue()));

    }

}
