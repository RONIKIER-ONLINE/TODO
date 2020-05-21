package online.ronikier.todo.interfaces.web;

import online.ronikier.todo.domain.Task;
import online.ronikier.todo.infrastructure.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.validation.Valid;

@Controller
public class TaskFormControler implements WebMvcConfigurer {

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


        Task newTask = new Task(taskForm.getName());

        Task millionDollarTask = taskRepository.findByName("Million Dollars");

        taskRepository.save(newTask);

        millionDollarTask.requiresCompletion(newTask);

        //taskRepository.(millionDollarTask);

        return "redirect:/results";
    }

}
