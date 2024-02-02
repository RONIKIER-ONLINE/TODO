package online.ronikier.todo.interfaces.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import online.ronikier.todo.Messages;
import online.ronikier.todo.domain.File;
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
import java.io.IOException;
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

    private final StorageService fileService;

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
    public String getTask(TaskForm taskForm, TaskFilterForm taskFilterForm, Model model) {
        if (!securityCheckOK(model)) return "login";
        refreshForm(taskForm, model);

        if (taskForm.getTask().getId() == null) {
            taskForm.setTask(taskService.initializeTask());
        }
        if (todoSetupDialogOn && taskService.countActiveTasks() > 0) {
            taskForm.setShowDialog(true);
            model.addAttribute("showDialog", true);
        }

        refreshAttributes(model);

        model.addAttribute("showTaskDetails", taskFilterForm.getShowDetails());

        model.addAttribute("taskList", getFilteredTaskList(taskFilterForm, SortOrder.PRIORITY));

        return "task";
    }

    @GetMapping(value = "task/{taskId}", produces = "text/html")
    public String getTaskById(@PathVariable(name = "taskId", required = false) Long taskId, TaskForm taskForm, TaskFilterForm taskFilterForm, Model model) {
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
        model.addAttribute("files", getTaskFileList(selectedTask.getId()));

        refreshForm(taskForm, model);

        taskForm.setRequiredTasks(getRequiredTaskList(selectedTask.getId()));

        taskForm.setFiles(getTaskFileList(selectedTask.getId()));

        model.addAttribute("showDialog", false);

        return "task";
    }

    /**
     * @param taskId
     * @return
     */
    private List<File> getTaskFileList(Long taskId) {
        if (taskId == null) return null;
        return fileService.taskFiles(taskId);
    }

    @PostMapping("task")
    public String postTask(@RequestParam("file") MultipartFile multipartFile,@Valid TaskForm taskForm, TaskFilterForm taskFilterForm, BindingResult bindingResult, Model model) {

        if (! multipartFile.isEmpty()) {
            try {
                storageService.store(multipartFile,taskForm.getTaskId());
            } catch (IOException e) {
                log.error(Messages.FILE_STORE_FAILED + e.getMessage());
                appendMessage(model, Messages.FILE_STORE_FAILED + Messages.SEPARATOR + e.getMessage());
            }
        }

//        redirectAttributes.addFlashAttribute("message",  "You successfully uploaded " + file.getOriginalFilename() + "!");
//
//        return "redirect:/file";

        if (!securityCheckOK(model)) return "/";

        if (taskForm.getAction() == null) taskForm.setAction(FormAction.FILTER);

        Task processedTask = null;

        // Tu na sznurek. Prowizora wieczna ...
        if (taskForm.getActionCommandButton() != null && !taskForm.getActionCommandButton().equals("CHUJ")) {
            log.warn((Messages.TASK_PROCESSING_CUSTOM_ACTION + Messages.SEPARATOR + taskForm.getActionCommandButton()));
            taskForm.setAction(FormAction.valueOf(taskForm.getActionCommandButton()));
        }

        //TODO: Refactor - Transfer to service
        switch (taskForm.getAction()) {
            case PROCES:
                procesorService.processTasks();
                taskForm.setTaskId(null);
                break;
            case TODAY:
                if (taskForm.getTaskId() == null) return "redirect:/task";
                processedTask = taskService.findTaskById(taskForm.getTaskId()).get();
                processedTask.setDue(Utilities.dateMorning());
                processedTask.setTaskStatus(TaskStatus.TODAY);
                processedTask.setTaskState(TaskState.STARTED);
                processedTask.setStart(Utilities.dateMorning());
                processedTask.setDue(Utilities.dateMorning());
                taskService.saveTask(processedTask);
                initializeForm(taskForm,model);
                taskForm.setTaskId(null);
                appendMessage(model, Messages.TASK_RESCHEDULED + Messages.SEPARATOR + processedTask.getName());
                break;
            case TOMMOROW:
                if (taskForm.getTaskId() == null) return "redirect:/task";
                processedTask = taskService.findTaskById(taskForm.getTaskId()).get();
                processedTask.setDue(Utilities.dateFuture(1));
                processedTask.setTaskStatus(TaskStatus.TOMMOROW);
                taskService.saveTask(processedTask);
                initializeForm(taskForm,model);
                taskForm.setTaskId(null);
                appendMessage(model, Messages.TASK_RESCHEDULED + Messages.SEPARATOR + processedTask.getName());
                break;
            case THIS_WEEKEND:
                if (taskForm.getTaskId() == null) return "redirect:/task";
                processedTask = taskService.findTaskById(taskForm.getTaskId()).get();
                processedTask.setDue(Utilities.dateNextWeekend());
                processedTask.setTaskStatus(TaskStatus.NEXT_WEEK);
                taskService.saveTask(processedTask);
                initializeForm(taskForm,model);
                taskForm.setTaskId(null);
                appendMessage(model, Messages.TASK_RESCHEDULED + Messages.SEPARATOR + processedTask.getName());
                break;
            case NEXT_WEEK:
                if (taskForm.getTaskId() == null) return "redirect:/task";
                processedTask = taskService.findTaskById(taskForm.getTaskId()).get();
                processedTask.setDue(Utilities.dateNextMondayMorning());
                processedTask.setTaskStatus(TaskStatus.NEXT_WEEK);
                taskService.saveTask(processedTask);
                initializeForm(taskForm,model);
                taskForm.setTaskId(null);
                appendMessage(model, Messages.TASK_RESCHEDULED + Messages.SEPARATOR + processedTask.getName());
                break;
            case NEXT_MONTH:
                if (taskForm.getTaskId() == null) return "redirect:/task";
                processedTask = taskService.findTaskById(taskForm.getTaskId()).get();
                processedTask.setDue(Utilities.dateNextMonth());
                processedTask.setTaskStatus(TaskStatus.NEXT_WEEK);
                taskService.saveTask(processedTask);
                initializeForm(taskForm,model);
                taskForm.setTaskId(null);
                appendMessage(model, Messages.TASK_RESCHEDULED + Messages.SEPARATOR + processedTask.getName());
                break;
            case FILTER:
                taskForm.setTask(taskService.initializeTask());
                initializeForm(taskForm,model);
                taskForm.setTaskId(null);
                break;
            case DELETE:
                if (taskForm.getTaskId() == null) return "redirect:/task";
                processedTask = taskService.findTaskById(taskForm.getTaskId()).get();
                taskService.deleteTaskById(processedTask.getId());
                initializeForm(taskForm,model);
                taskForm.setTaskId(null);
                appendMessage(model, Messages.TASK_DELETING + Messages.SEPARATOR + processedTask.getName());
                break;
            case STOP:
                if (taskForm.getTaskId() == null) return "redirect:/task";
                processedTask = taskService.findTaskById(taskForm.getTaskId()).get();
                processedTask.setTaskState(TaskState.ON_HOLD);
                processedTask.setStop(Utilities.dateMorning());
                taskService.saveTask(processedTask);
                taskForm.setTaskId(null);
                initializeForm(taskForm,model);
                break;
            case START:
                if (taskForm.getTaskId() == null) return "redirect:/task";
                processedTask = taskService.findTaskById(taskForm.getTaskId()).get();
                processedTask.setTaskState(TaskState.STARTED);
                processedTask.setDue(Utilities.dateFuture(todoSetupProcessorTaskApproaching));
                processedTask.setStart(Utilities.dateMorning());
                taskService.saveTask(processedTask);
                initializeForm(taskForm,model);
                taskForm.setTaskId(null);
                break;
            case REJECT:
                if (taskForm.getTaskId() == null) return "redirect:/task";
                processedTask = taskService.findTaskById(taskForm.getTaskId()).get();
                processedTask.setTaskState(TaskState.REJECTED);
                processedTask.setDue(null);
                taskService.saveTask(processedTask);
                initializeForm(taskForm,model);
                taskForm.setTaskId(null);
                break;
            case COMPLETE: {
                if (taskForm.getTaskId() == null) return "redirect:/task";
                processedTask = taskService.findTaskById(taskForm.getTaskId()).get();
                processedTask.setTaskState(TaskState.COMPLETED);
                processedTask.setTaskStatus(TaskStatus.OK);
                taskService.saveTask(processedTask);
                initializeForm(taskForm,model);
                taskForm.setTaskId(null);
                break;
            }
            case SAVE: {
                    if (bindingResult.hasErrors()) {
                        StringBuffer errorMessagesBuffer = new StringBuffer(0);
                        log.error(Messages.ERROR_TASK_ADD);
                        bindingResult.getAllErrors().forEach(error -> {
                            log.error(Messages.SEPARATOR + error.getDefaultMessage());
                            errorMessagesBuffer.append(
                                    error.getObjectName() + Messages.HTML_BR + Messages.HTML_BR +
                                            error.getDefaultMessage());
                        });

                        appendMessage(model, errorMessagesBuffer.toString());
                        break;
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
                break;
            }
            case UPLOAD: {
                if (taskForm.getTaskId() == null) return "redirect:/task";
                processedTask = taskService.findTaskById(taskForm.getTaskId()).get();
                processedTask.setTaskState(TaskState.COMPLETED);
                processedTask.setTaskStatus(TaskStatus.OK);
                taskService.saveTask(processedTask);
                break;
            }
            default: {
                if (taskForm.getTask() == null) {
                    taskForm.setTask(taskService.initializeTask());
//                    model.addAttribute("files", storageService.loadTaskFiles(processedTask).map(
//                                    path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
//                                            "serveFile", path.getFileName().toString()).build().toUri().toString())
//                            .collect(Collectors.toList()));
                }
            }
        }

        refreshForm(taskForm, model);

        refreshAttributes(model);

        if (todoSetupDialogOn && taskService.countActiveTasks() > 0) {
            taskForm.setShowDialog(true);
            model.addAttribute("showDialog", true);
        }

        return "task";
    }

    @PostMapping("filter")
    public String postFilter(TaskFilterForm taskFilterForm, BindingResult bindingResult, Model model) {
        model.addAttribute("taskList", getFilteredTaskList(taskFilterForm, SortOrder.PRIORITY));
        refreshAttributes(model);

        model.addAttribute("showTaskDetails", taskFilterForm.getShowDetails());

        return "task";
    }


    private void appendMessage(Model model, String messag) {
        model.addAttribute("dialogMessage",
                model.getAttribute("dialogMessage") + Messages.HTML_BR + Messages.HTML_BR +
                        messag);

        model.addAttribute("showDialog", true);
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
        TaskFilterForm taskFilterForm = new TaskFilterForm();
        taskFilterForm.setShowDetails(false);
        taskForm.setTaskFilterForm(taskFilterForm);

        //taskForm.setShowTaskDetails(false);
        refreshForm(taskForm, model);
        taskForm.setAction(FormAction.FILTER);
    }

    /**
     * @param taskForm
     * @param model
     */
    private void refreshForm(TaskForm taskForm, Model model) {

        taskForm.setTasks(getTaskList(SortOrder.NAME));
        taskForm.setPersons(getPersonList());

        refreshAttributes(model);

    }

    private void refreshAttributes(Model model) {

        model.addAttribute("showTaskDetails", false);

        model.addAttribute("taskListDialog", getDialogTaskList());
        model.addAttribute("taskListCounter", Utilities.counter());
        model.addAttribute("taskCount", taskService.countTasks());
        long activeTaskCount =  taskService.countActiveTasks();
        model.addAttribute("taskCountActive", activeTaskCount);
        model.addAttribute("dialogMessage", "");
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
//    @GetMapping("/files/{filename:.+}")
//    @ResponseBody
//    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
//
//        Resource file = storageService.loadAsResource(filename);
//        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
//                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
//    }
}
