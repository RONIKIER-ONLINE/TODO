package online.ronikier.todo.interfaces.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TaskController {

    @GetMapping("/todo")
    public String greeting(@RequestParam(name="taskNumber", required=false, defaultValue="Task 1") String name, Model model) {
        model.addAttribute("taskNumber", name);
        return "todo";
    }

}
