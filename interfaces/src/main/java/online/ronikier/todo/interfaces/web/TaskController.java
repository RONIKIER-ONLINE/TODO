package online.ronikier.todo.interfaces.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import online.ronikier.todo.library.Parameters;
import online.ronikier.todo.domain.Task;
import online.ronikier.todo.domain.forms.TaskForm;
import online.ronikier.todo.infrastructure.TaskRepository;
import online.ronikier.todo.interfaces.base.AbstractController;
import online.ronikier.todo.Messages;
import online.ronikier.todo.interfaces.mappers.TaskMapper;
import online.ronikier.todo.library.Utilities;
import org.mapstruct.factory.Mappers;
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
import java.text.ParseException;

/**
 *
 */
@Slf4j
@Controller
@PropertySource("classpath:controller.properties")
@RequiredArgsConstructor
public class TaskController extends AbstractController  {

    @Value("${task.completion.time.days:7}")
    private Integer taskCompletionTimeDays;

    private final TaskRepository taskRepository;

    /**
     *
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/" + Parameters.WEB_CONTROLLER_TASK).setViewName(Parameters.WEB_CONTROLLER_TASK);
        registry.addViewController("/" + Parameters.WEB_CONTROLLER_TASK + Parameters.WEB_CONTROLLER_OPERATION_DELETE).setViewName(Parameters.WEB_CONTROLLER_TASK);
    }

    /**
     *
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
     *
     * @param taskId
     * @param taskForm
     * @param model
     * @return
     */
    @GetMapping(Parameters.WEB_CONTROLLER_TASK + "/" + "{" + Parameters.WEB_CONTROLLER_PARAMETER_TASK_ID + "}")
    public String taskShow(@PathVariable(name = Parameters.WEB_CONTROLLER_PARAMETER_TASK_ID,required = false) Long taskId, TaskForm taskForm, Model model) {
        Task selectedTask = taskRepository.findById(taskId).get();
        if (selectedTask == null) {
            log.info(Messages.REPOSITORY_TASK_NOT_FOUND + Messages.SEPARATOR + taskId);
            return Parameters.WEB_CONTROLLER_TASK;
        }

        initializeForm(taskForm, model);

        updateForm(taskForm, selectedTask);

        return Parameters.WEB_CONTROLLER_TASK;
    }

    /**
     *
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
            initializeForm(taskForm,model);
            return Parameters.WEB_CONTROLLER_TASK;
        }
        try {

            Task processedTask = taskRepository.findByName(taskForm.getName());

            if (processedTask != null) {
                log.info(Messages.INFO_TASK_EXIST);
                processedTask = Mappers.getMapper(TaskMapper.class).form2Domain(taskForm);
                taskRepository.save(processedTask);
                return Parameters.WEB_CONTROLLER_TASK;
            }

            Task newTask = initializeTask(taskForm);
            taskRepository.save(newTask);

            Task uberTask = taskRepository.findById(Long.valueOf(taskForm.getUberTaskId())).get();
            uberTask.requires(newTask);
            taskRepository.save(uberTask);

            log.info(Messages.INFO_TASK_CREATED);

        } catch (Exception e) {
            log.error(Messages.EXCEPTION_TASK_CREATION + Messages.SEPARATOR + e.getMessage());
        }

        initializeForm(taskForm,model);

        return Parameters.WEB_CONTROLLER_TASK;

    }

    /**
     *
     * @param taskId
     * @return
     */
    @GetMapping(Parameters.WEB_CONTROLLER_TASK + Parameters.WEB_CONTROLLER_OPERATION_DELETE + "/" + "{" + Parameters.WEB_CONTROLLER_PARAMETER_TASK_ID + "}")
    public String taskDelete(@PathVariable(name = "taskId",required = false) Long taskId, TaskForm taskForm, Model model) {
        log.info(Messages.INFO_TASK_DELETING);
        taskRepository.deleteById(taskId);
        initializeForm(taskForm,model);
        return Parameters.WEB_CONTROLLER_TASK;
    }

    /**
     *
     * @param taskForm
     * @param bindingResult
     * @param model
     * @return
     */
    @PostMapping(Parameters.WEB_CONTROLLER_TASK + Parameters.WEB_CONTROLLER_OPERATION_SAVE)
    public String taskSave(@Valid TaskForm taskForm, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            log.error(Messages.ERROR_TASK_ADD);
            bindingResult.getAllErrors().forEach(error -> log.error(Messages.SEPARATOR + error.toString()));
            return Parameters.WEB_CONTROLLER_TASK;
        }

        try {

            Task modifiedTask = taskRepository.findByName(taskForm.getName());

            if (modifiedTask == null) {
                log.error(Messages.REPOSITORY_TASK_NOT_FOUND + Messages.SEPARATOR + modifiedTask);
                return Parameters.WEB_CONTROLLER_TASK;
            }

            modifiedTask = Mappers.getMapper(TaskMapper.class).form2Domain(taskForm);
            taskRepository.save(modifiedTask);

            log.info(Messages.INFO_TASK_MODIFIED);

        } catch (Exception e) {
            log.error(Messages.EXCEPTION_TASK_CREATION + Messages.SEPARATOR + e.getMessage());
        }
        return Parameters.WEB_CONTROLLER_TASK;
    }

    /**
     *
     * @param taskForm
     * @return
     * @throws ParseException
     */
    private Task initializeTask(TaskForm taskForm) throws ParseException {
        return new Task(
                taskForm.getImportant(),
                taskForm.getUrgent(),
                Utilities.dateFromString(taskForm.getCreated()),
                Utilities.dateFromString(taskForm.getStart()),
                Utilities.dateFromString(taskForm.getDue()),
                taskForm.getName(),
                taskForm.getDescription());
    }

    /**
     *
     * @param taskForm
     * @param model
     */
    private void initializeForm(TaskForm taskForm, Model model) {

        model.addAttribute("allTasks",taskRepository.findAll());

        taskForm.setUberTasks(taskRepository.findAll());

        model.addAttribute("taskCount",taskRepository.count());

        taskForm.setCreated(Utilities.stringFromDate(Utilities.dateCurrent()));

        taskForm.setStart(Utilities.stringFromDate(Utilities.dateCurrent()));
        taskForm.setDue(Utilities.stringFromDate(Utilities.dateFuture(taskCompletionTimeDays)));

    }

    private void updateForm(TaskForm taskForm, Task task) {

        // TODO: Date convertion - See TaskMapper
        // TaskMapper.INSTANCE.domain2Form(task, taskForm);

        taskForm.setName(task.getName());
        taskForm.setDescription(task.getDescription());
        taskForm.setUrgent(task.getUrgent());
        taskForm.setImportant(task.getImportant());
        taskForm.setCreated(Utilities.stringFromDate(task.getCreated()));
        taskForm.setStart(Utilities.stringFromDate(task.getStart()));
        taskForm.setDue(Utilities.stringFromDate(task.getDue()));

    }

}
