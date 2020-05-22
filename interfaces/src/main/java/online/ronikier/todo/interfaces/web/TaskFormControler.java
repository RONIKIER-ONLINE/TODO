package online.ronikier.todo.interfaces.web;

import lombok.extern.slf4j.Slf4j;
import online.ronikier.todo.TodoConfiguration;
import online.ronikier.todo.config.Messages;
import online.ronikier.todo.domain.Task;
import online.ronikier.todo.infrastructure.TaskRepository;
import online.ronikier.todo.library.Utilities;
import online.ronikier.todo.templates.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;

import javax.validation.Valid;

@Slf4j
@Controller
public class TaskFormControler extends BaseController {

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/results").setViewName("results");
    }

    @GetMapping("/task")
    public String showForm(TaskForm taskForm) {
        return "task";
    }

    @PostMapping("/task")
    public String checkTaskInfo(@Valid TaskForm taskForm, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "task";
        }

        try {

            Task newTask = new Task(taskForm.getImportant(), taskForm.getUrgent(), Utilities.dateFromString(taskForm.getCreated()), Utilities.dateFromString(taskForm.getDue()), taskForm.getName(), taskForm.getDescription(), TodoConfiguration.dafaultTasks());

            Task millionDollarTask = taskRepository.findByName("Be Rich");

            millionDollarTask.requires(newTask);

            taskRepository.save(newTask);

            taskRepository.save(millionDollarTask);

        } catch (Exception e) {
            log.error(Messages.EXCEPTION_TASK_CREATION + Messages.SEPARATOR + e.getMessage());
        }

        return "redirect:/results";
    }

}
