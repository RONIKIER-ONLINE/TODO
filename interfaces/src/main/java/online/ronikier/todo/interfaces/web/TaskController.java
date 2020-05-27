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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;

import javax.validation.Valid;

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

        model.addAttribute("allTasks",taskRepository.findAll());

        return "task";
    }

    @PostMapping("/task")
    public String taskAdd(@Valid TaskForm taskForm, BindingResult bindingResult, Model model) {

        model.addAttribute("allTasks",taskRepository.findAll());

        model.addAttribute("taskCount",taskRepository.count());

        if (bindingResult.hasErrors()) {
            log.error(Messages.ERROR_TASK_ADD);
            bindingResult.getAllErrors().forEach(error -> log.error(Messages.SEPARATOR + error.toString()));
            return "task";
        }

        if (taskRepository.findByName(taskForm.getName()) != null) {
            log.error(Messages.ERROR_TASK_ADD + Messages.SEPARATOR + Messages.ERROR_TASK_EXIST);
            return "task";
        }

        if (taskForm.getCreated() == null) {
            log.error(Messages.ERROR_TASK_ADD + Messages.SEPARATOR + Messages.ERROR_TASK_EXIST);
            return "task";
        }

        if (taskForm.getDue() == null) {
            log.error(Messages.ERROR_TASK_ADD + Messages.SEPARATOR + Messages.ERROR_TASK_EXIST);
            return "task";
        }

        try {

            Task newTask =
                    new Task(
                            taskForm.getImportant(),
                            taskForm.getUrgent(),
                            Utilities.dateFromString(taskForm.getCreated()),
                            Utilities.dateFromString(taskForm.getStart()),
                            Utilities.dateFromString(taskForm.getDue()),
                            taskForm.getName(),
                            taskForm.getDescription());

            taskRepository.save(newTask);

            Task millionDollarTask = taskRepository.findByName("Be Rich");

            millionDollarTask.requires(newTask);

            taskRepository.save(millionDollarTask);

            log.info(Messages.INFO_TASK_CREATED);

        } catch (Exception e) {
            log.error(Messages.EXCEPTION_TASK_CREATION + Messages.SEPARATOR + e.getMessage());
        }

        return "task";

    }

}
