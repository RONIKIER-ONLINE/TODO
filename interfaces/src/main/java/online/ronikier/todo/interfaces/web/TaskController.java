package online.ronikier.todo.interfaces.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import online.ronikier.todo.Messages;
import online.ronikier.todo.domain.Person;
import online.ronikier.todo.domain.Task;
import online.ronikier.todo.domain.dictionary.SortOrder;
import online.ronikier.todo.domain.dictionary.TaskState;
import online.ronikier.todo.domain.dictionary.TaskStatus;
import online.ronikier.todo.domain.dictionary.utility.LegendsRepository;
import online.ronikier.todo.domain.forms.TaskFilterForm;
import online.ronikier.todo.domain.forms.TaskForm;
import online.ronikier.todo.infrastructure.service.PersonService;
import online.ronikier.todo.infrastructure.service.ProcesorService;
import online.ronikier.todo.infrastructure.service.TaskService;
import online.ronikier.todo.infrastructure.storage.StorageService;
import online.ronikier.todo.interfaces.mappers.TaskMapper;
import online.ronikier.todo.library.Parameters;
import online.ronikier.todo.library.Utilities;
import online.ronikier.todo.templete.FormAction;
import online.ronikier.todo.templete.SuperController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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

    private final StorageService storageService;

    private final ProcesorService procesorService;

    @Value("${todo.setup.dialog.on:0}")
    private Boolean todoSetupDialogOn;

    @Value("${task.completion.time.days:7}")
    private Integer taskCompletionTimeDays;

    @Value("${todo.setup.processor.task.approaching.days:3}")
    private Integer todoSetupProcessorTaskApproaching;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/task").setViewName("task");
        registry.addViewController("/task_delete").setViewName("task");
    }

    @GetMapping(value = "task", produces = "text/html")
    public String getTask(TaskForm taskForm, Model model) {
        if (!securityCheckOK(model)) return "login";
        refreshForm(taskForm, model);
        if (taskForm.getTask().getId() == null) {
            taskForm.setTask(taskService.initializeTask());
        }
        if (todoSetupDialogOn && taskService.countActiveTasks() > 0) {
            taskForm.setShowDialog(true);
            model.addAttribute("showDialog", true);
        }
        return "task";
    }

    @GetMapping(value = "task/{taskId}", produces = "text/html")
    public String getTaskById(@PathVariable(name = "taskId", required = false) Long taskId, TaskForm taskForm, Model model) {
        if (!securityCheckOK(model)) return "/";
        Optional<Task> optionalTask = taskService.findTaskById(taskId);
        if (!optionalTask.isPresent()) {
            log.info(Messages.REPOSITORY_TASK_NOT_FOUND + Messages.SEPARATOR + taskId);
            return "task";
        }
        Task selectedTask = optionalTask.get();
        //initializeForm(taskForm, model);
        updateForm(taskForm, selectedTask);
        model.addAttribute("tasksRequiredTasks", getRequiredTaskList(selectedTask.getId()));
        refreshForm(taskForm, model);

        model.addAttribute("showDialog", false);

        return "task";
    }

    @PostMapping("task")
    public String postTask(@RequestParam("file") MultipartFile file,@Valid TaskForm taskForm, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {

        try {
            storageService.store(file);
        } catch (Exception e) {
            log.error("ZJEBAŁO SIE:" + e.getMessage());
        }
//        redirectAttributes.addFlashAttribute("message",  "You successfully uploaded " + file.getOriginalFilename() + "!");
//
//        return "redirect:/file";

        if (!securityCheckOK(model)) return "/";

        if (taskForm.getAction() == null) taskForm.setAction(FormAction.FILTER);

        Task processedTask;

        // Tu na sznurek. Prowizora wieczna ...
        if (taskForm.getActionCommandButton() != null && !taskForm.getActionCommandButton().equals("CHUJ")) {
            log.warn((Messages.TASK_PROCESSING_CUSTOM_ACTION + Messages.SEPARATOR + taskForm.getActionCommandButton()));
            taskForm.setAction(FormAction.valueOf(taskForm.getActionCommandButton()));
        }

        //TODO: Refactor - Transfer to service
        switch (taskForm.getAction()) {
            case PROCES:
                procesorService.processTasks();
                break;
            case TODAY:
                processedTask = taskService.findTaskById(taskForm.getTaskId()).get();
                processedTask.setDue(Utilities.dateCurrent());
                processedTask.setTaskStatus(TaskStatus.TODAY);
                processedTask.setTaskState(TaskState.STARTED);
                processedTask.setStart(Utilities.dateCurrent());
                taskService.saveTask(processedTask);
                break;
            case TOMMOROW:
                processedTask = taskService.findTaskById(taskForm.getTaskId()).get();
                processedTask.setDue(Utilities.dateFuture(1));
                processedTask.setTaskStatus(TaskStatus.TOMMOROW);
                taskService.saveTask(processedTask);
                break;
            case NEXT_WEEK:
                processedTask = taskService.findTaskById(taskForm.getTaskId()).get();
                processedTask.setDue(Utilities.dateFuture(7));
                processedTask.setTaskStatus(TaskStatus.THIS_WEEK);
                taskService.saveTask(processedTask);
                break;
            case FILTER:
                break;
            case DELETE:
                processedTask = taskService.findTaskById(taskForm.getTaskId()).get();
                taskService.deleteTaskById(processedTask.getId());
                initializeForm(taskForm,model);
                break;
            case STOP:
                processedTask = taskService.findTaskById(taskForm.getTaskId()).get();
                processedTask.setTaskState(TaskState.ON_HOLD);
                processedTask.setStop(Utilities.dateCurrent());
                taskService.saveTask(processedTask);
                break;
            case START:
                processedTask = taskService.findTaskById(taskForm.getTaskId()).get();
                processedTask.setTaskState(TaskState.STARTED);
                processedTask.setDue(Utilities.dateFuture(todoSetupProcessorTaskApproaching));
                processedTask.setStart(Utilities.dateCurrent());
                taskService.saveTask(processedTask);
                break;
            case REJECT:
                processedTask = taskService.findTaskById(taskForm.getTaskId()).get();
                processedTask.setTaskState(TaskState.REJECTED);
                processedTask.setDue(null);
                taskService.saveTask(processedTask);
                break;
            case COMPLETE: {
                processedTask = taskService.findTaskById(taskForm.getTaskId()).get();
                processedTask.setTaskState(TaskState.COMPLETED);
                processedTask.setTaskStatus(TaskStatus.OK);
                taskService.saveTask(processedTask);
                break;
            }
            case SAVE: {
                if (taskForm.getActionCommandButton() == null) {
                    if (bindingResult.hasErrors()) {
                        log.error(Messages.ERROR_TASK_ADD);
                        bindingResult.getAllErrors().forEach(error -> log.error(Messages.SEPARATOR + error.toString()));
                        initializeForm(taskForm, model);
                        break;
                    }
                }
                try {
                    Optional<Task> optionalTask = taskService.findTaskByName(taskForm.getName());
                    //processedTask = Parameters.SYSTEM_ALLOW_DUPLICATE_NAMES
                    //        ? taskService.findTaskByName(taskForm.getName())
                    // TODO: Fix null pointer by new task - introduce New button (visible/disabled)
                    //        : taskService.findTaskById(taskForm.getTaskId()).get();
                    if (optionalTask.isPresent()) {
                        log.info((Messages.TASK_EXISTS));
                        processedTask = optionalTask.get();
                        log.debug(processedTask.toString());
                    } else {
                        processedTask = taskService.initializeTask();
                        log.info(Messages.TASK_CREATED);
                    }
                    saveTask(taskForm, processedTask);
                    log.debug(processedTask.toString());
                } catch (Exception e) {
                    taskForm.setShowDialog(true);
                    model.addAttribute("dialogMessage", e.getMessage());
                    log.error(Messages.EXCEPTION_TASK_CREATION + Messages.SEPARATOR + e.getMessage());
                }
            }
            case UPLOAD: {
                processedTask = taskService.findTaskById(taskForm.getTaskId()).get();
                processedTask.setTaskState(TaskState.COMPLETED);
                processedTask.setTaskStatus(TaskStatus.OK);
                taskService.saveTask(processedTask);
                break;
            }
            default: {
                if (taskForm.getTask() == null) {
                    taskForm.setTask(taskService.initializeTask());
                    model.addAttribute("files", storageService.loadAll().map(
                                    path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
                                            "serveFile", path.getFileName().toString()).build().toUri().toString())
                            .collect(Collectors.toList()));
                }
            }
        }

        refreshForm(taskForm, model);

        if (todoSetupDialogOn && taskService.countActiveTasks() > 0) {
            taskForm.setShowDialog(true);
            model.addAttribute("showDialog", true);
        }

        return "task";
    }

    @GetMapping(value = "task_delete/{taskId}", produces = "text/html")
    public String getTaskDelete(@PathVariable(name = "taskId", required = false) Long taskId, TaskForm taskForm, Model model) {

        if (!securityCheckOK(model)) return "/";

        log.info(Messages.TASK_DELETING);
        taskService.deleteTaskById(taskId);
        initializeForm(taskForm, model);
        model.addAttribute("tasksRequiredTasks", null);
        return "task";
    }

    /**
     * @param taskForm
     * @param task
     */
    @Transactional
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
                taskService.saveTask(task);
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

        taskForm.getTask().setTaskType(task.getTaskType());
        taskForm.getTask().setTaskState(task.getTaskState());

        log.info(Messages.TASK_MODIFIED);
        log.debug(task.toString());
    }

    /**
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
     * @param taskId
     * @return
     */
    private Iterable<Task> getRequiredTaskList(Long taskId) {
        log.error(Messages.DEV_IMPLEMENT_ME + Messages.SEPARATOR + "Required task filtering");
        if (taskId == null) return null;
        return taskService.tasksRequiredTasks(taskId);
    }

    /**
     * @return
     */
    private List<Task> getTaskList(SortOrder sortOrder) {
        return taskService.allTasks(sortOrder);
    }

    /**
     * @return
     */
    private List<Task> getFilteredTaskList(TaskFilterForm taskFilterForm, SortOrder sortOrder) {
        return taskService.filteredTasks(taskFilterForm, sortOrder);
    }


    /**
     * @param taskForm
     * @param model
     */
    private void initializeForm(TaskForm taskForm, Model model) {
        taskForm.setTask(taskService.initializeTask());
        taskForm.setTaskFilterForm(new TaskFilterForm());
        //taskForm.setShowTaskDetails(false);
        refreshForm(taskForm, model);
        taskForm.setAction(FormAction.FILTER);
    }

    /**
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
        model.addAttribute("taskList", getFilteredTaskList(taskForm.getTaskFilterForm(), taskListSortOrder));

        model.addAttribute("taskListDialog", getDialogTaskList());

        model.addAttribute("taskListCounter", Utilities.counter());

        taskForm.setTasks(getTaskList(SortOrder.NAME));
        taskForm.setPersons(getPersonList());

        model.addAttribute("taskCount", taskService.countTasks());
        long activeTaskCount =  taskService.countActiveTasks();
        model.addAttribute("taskCountActive", activeTaskCount);

        model.addAttribute("dialogMessage", ">>>:" + model.getAttribute("dialogMessage"));

        model.addAttribute("infoTaskStatus", LegendsRepository.taskStatusLegend());

        model.addAttribute("infoTaskState", LegendsRepository.taskStateLegeng());

    }

    private List<Task> getDialogTaskList() {
        return taskService.activeTasks();
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

        //targetTaskForm.setTask(sourceTask);

//        targetTaskForm.setName(sourceTask.getName());
//        targetTaskForm.setDescription(sourceTask.getDescription());
//        targetTaskForm.setUrgent(sourceTask.getUrgent());
//        targetTaskForm.setImportant(sourceTask.getImportant());
//        targetTaskForm.setCreated(Utilities.stringFromDate(sourceTask.getCreated()));
//        targetTaskForm.setStart(Utilities.stringFromDate(sourceTask.getStart()));
//        targetTaskForm.setDue(Utilities.stringFromDate(sourceTask.getDue()));

    }
    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
}
