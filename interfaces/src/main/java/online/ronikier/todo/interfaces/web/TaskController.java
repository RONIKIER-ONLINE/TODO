package online.ronikier.todo.interfaces.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import online.ronikier.todo.Messages;
import online.ronikier.todo.domain.Person;
import online.ronikier.todo.domain.Task;
import online.ronikier.todo.domain.dictionary.*;
import online.ronikier.todo.domain.forms.TaskFilterForm;
import online.ronikier.todo.domain.forms.TaskForm;
import online.ronikier.todo.infrastructure.service.PersonService;
import online.ronikier.todo.infrastructure.service.TaskService;
import online.ronikier.todo.interfaces.mappers.TaskMapper;
import online.ronikier.todo.library.Parameters;
import online.ronikier.todo.library.Utilities;
import online.ronikier.todo.templete.FormAction;
import online.ronikier.todo.templete.SuperController;
import online.ronikier.todo.templete.SuperForm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;

import javax.validation.Valid;
import java.util.*;

@Slf4j
@Controller
@PropertySource("classpath:controller.properties")
@RequiredArgsConstructor
public class TaskController extends SuperController {

    /**      * @deprecated (when, why, forRemoval)      */
    @Deprecated(forRemoval = true)
    private final Task devTask;

    private final Set<Task> dafaultTasks;

    private final TaskService taskService;

    private final TaskMapper taskMapper;

    private final PersonService personService;

    @Value("${task.completion.time.days:7}")
    private Integer taskCompletionTimeDays;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/" + Parameters.WEB_CONTROLLER_TASK).setViewName(Parameters.WEB_CONTROLLER_TASK);
        registry.addViewController("/" + Parameters.WEB_CONTROLLER_TASK + Parameters.WEB_CONTROLLER_OPERATION_DELETE).setViewName(Parameters.WEB_CONTROLLER_TASK);
    }

    @GetMapping(value = Parameters.WEB_CONTROLLER_TASK, produces = "text/html")
    public String getTask(TaskForm taskForm, Model model) {
        refreshForm(taskForm, model);
        if (taskForm.getTask() == null) {
            taskForm.setTask(initializeTask());
        }
        return Parameters.WEB_CONTROLLER_TASK;
    }

    @GetMapping(value = Parameters.WEB_CONTROLLER_TASK + "/" + "{" + Parameters.WEB_CONTROLLER_PARAMETER_TASK_ID + "}", produces = "text/html")
    public String getTaskById(@PathVariable(name = Parameters.WEB_CONTROLLER_PARAMETER_TASK_ID, required = false) Long taskId, TaskForm taskForm, Model model) {
        Optional<Task> optionalTask = taskService.findTaskById(taskId);
        if (!optionalTask.isPresent()) {
            log.info(Messages.REPOSITORY_TASK_NOT_FOUND + Messages.SEPARATOR + taskId);
            return Parameters.WEB_CONTROLLER_TASK;
        }
        Task selectedTask = optionalTask.get();
        updateForm(taskForm, selectedTask);
        model.addAttribute("tasksRequiredTasks", getRequiredTaskList(selectedTask.getId()));
        refreshForm(taskForm, model);
        return Parameters.WEB_CONTROLLER_TASK;
    }

    @PostMapping(Parameters.WEB_CONTROLLER_TASK)
    public String postTask(@Valid TaskForm taskForm, BindingResult bindingResult, Model model) {

        if (FormAction.SAVE.equals(taskForm.getAction())) {
            if (bindingResult.hasErrors()) {
                log.error(Messages.ERROR_TASK_ADD);
                StringBuilder dialogMessageBuilder = new StringBuilder();
                bindingResult.getAllErrors().forEach(error -> dialogMessageBuilder.append(Messages.SEPARATOR + error.toString()));
                log.error(dialogMessageBuilder.toString());
//                initializeDialog(model, dialogMessageBuilder.toString());
//                initializeForm(taskForm, model);
//                return Parameters.WEB_CONTROLLER_TASK;
            }
            saveTask(taskForm,initializeTask());
            refreshForm(taskForm, model);
            return Parameters.WEB_CONTROLLER_TASK;
        }

        Optional<Task> processedTaskOptional = null;
        if (taskForm.getTask() != null && !FormAction.SAVE.equals(taskForm.getAction())) {
            processedTaskOptional = taskService.findTaskById(taskForm.getTaskId());
            switch (taskForm.getAction()) {
                case DELETE:
                    taskService.deleteTaskById(processedTaskOptional.get().getId());
                    initializeForm(taskForm, model);
                    break;
                case REJECT:
                    taskService.processReject(processedTaskOptional.get());
                    break;
                case FILTER:
                    break;
                case COMPLETE:
                    taskService.processComplete(processedTaskOptional.get());
                    break;
                case SAVE:
                    saveTask(taskForm,taskService.processSave(processedTaskOptional.get(),taskForm.getName()));
                    break;
                default:
                    log.debug("NO ACTION ?");
                    taskForm.setTask(initializeTask());
                    refreshForm(taskForm, model);
            }
        }
        refreshForm(taskForm, model);
        return Parameters.WEB_CONTROLLER_TASK;
    }

    private void initializeDialog(Model model, String dialogMessage) {
        model.addAttribute("showDialog",true);
        model.addAttribute("dialogMessage", dialogMessage);
    }

    @GetMapping(value = Parameters.WEB_CONTROLLER_TASK + Parameters.WEB_CONTROLLER_OPERATION_DELETE + "/" + "{" + Parameters.WEB_CONTROLLER_PARAMETER_TASK_ID + "}", produces = "text/html")
    public String getTaskDelete(@PathVariable(name = "taskId", required = false) Long taskId, TaskForm taskForm, Model model) {
        log.info(Messages.TASK_DELETING);
        taskService.deleteTaskById(taskId);
        initializeForm(taskForm, model);
        resetModel(model);
        return Parameters.WEB_CONTROLLER_TASK;
    }


    private Task initializeTask() {

        return new Task(
                appendMaintanceTasks(),
                null,
                null,
                null,
                null,
                null,
                Utilities.dateCurrent(),
                null,       //Utilities.dateCurrent(),
                null,       //Utilities.dateFuture(taskCompletionTimeDays),
                null,
                null,
                Double.valueOf(1),
                CostUnit.SOLDIER,
                TaskState.NEW,
                TaskType.GENERAL,
                TaskStatus.OK
        );

    }

    @Override
    protected void resetModel(Model model) {
        super.resetModel(model);
        model.addAttribute("tasksRequiredTasks", null);
        model.addAttribute("taskListCounter", Utilities.counter());
        model.addAttribute("taskCount", taskService.countTasks());
        model.addAttribute("interfaceWidth", Parameters.INTERFACE_WIDTH);
    }

    @Override
    protected void initializeForm(SuperForm form, Model model) {

        super.initializeForm(form,model);

        TaskForm taskForm = (TaskForm)form;

        taskForm.setTask(initializeTask());
        ((TaskForm)form).setTaskFilterForm(new TaskFilterForm());
        refreshForm(form, model);
    }

    @Override
    protected void refreshForm(SuperForm form, Model model) {

        SortOrder taskListSortOrder = SortOrder.DEFAULT;

        TaskForm taskForm = (TaskForm)form;

        if (taskForm.getShowTaskDetails() != null && taskForm.getShowTaskDetails()) {
            model.addAttribute("showTaskDetails", true);
        } else {
            taskListSortOrder = SortOrder.NAME;
        }

        taskForm.setTasks(getTaskList(SortOrder.NAME));
        taskForm.setRequiredTasks(getTaskList(SortOrder.NAME));
        taskForm.setPersons(getPersonList());

        model.addAttribute("taskList", getFilteredTaskList(taskForm.getTaskFilterForm(), taskListSortOrder));

        resetModel(model);

    }

    private void saveTask(TaskForm taskForm, Task task) {
        log.info(Messages.TASK_MODIFIED);
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
                log.info(Messages.SKIPPING_NOT_REQUIRED);
                return;
            }
        }

        taskForm.setTask(task);
        log.info(Messages.TASK_MODIFIED);
        log.debug(task.toString());
    }

    private void assignResponsible(TaskForm taskForm, Task task) {
        Optional<Person> responsibleOptional = personService.findPersonById(Long.valueOf(taskForm.getResponsiblePersonId()));
        if (responsibleOptional.isPresent()) {
            task.isDoneBy(responsibleOptional.get());
            log.debug(Messages.DEBUG_MESSAGE_PREFIX + Messages.SEPARATOR + task + Messages.SEPARATOR + responsibleOptional.get());
        }
    }

    private List<Task> appendMaintanceTasks() {
        if (Parameters.SYSTEM_SKIP_MAINTENANCE_TASKS) {
            log.info(Messages.SKIPPING_MAINTENANCE_TASKS);
            return Collections.emptyList();
        }
        return taskService.getMaintanceTasks();
    }

    private Iterable<Task> getRequiredTaskList(Long taskId) {
        log.error(Messages.DEV_IMPLEMENT_ME + Messages.SEPARATOR + "Required task filtering");
        if (taskId == null) return null;
        return taskService.tasksRequiredTasks(taskId);
    }

    private List<Task> getTaskList(SortOrder sortOrder) {
        return taskService.allTasks(sortOrder);
    }

    private List<Task> getFilteredTaskList(TaskFilterForm taskFilterForm, SortOrder sortOrder) {
        return taskService.filteredTasks(taskFilterForm, sortOrder);
    }

    private List<Person> getPersonList() {

        return personService.allPersons();
    }

    /**
     * @param targetTaskForm
     * @param sourceTask
     */
    private void updateForm(TaskForm targetTaskForm, Task sourceTask) {

        taskMapper.domain2Form(sourceTask, targetTaskForm);

    }

}
