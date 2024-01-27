package online.ronikier.todo.interfaces.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import online.ronikier.todo.Messages;
import online.ronikier.todo.domain.exception.DataException;
import online.ronikier.todo.domain.exception.ParameterException;
import online.ronikier.todo.infrastructure.service.DialogService;
import online.ronikier.todo.infrastructure.service.TaskService;
import online.ronikier.todo.templete.SuperController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 *
 */
@Slf4j
@Controller
@PropertySource("classpath:controller.properties")
@RequiredArgsConstructor
public class DialogControler extends SuperController {

    private final DialogService dialogService;

    private final TaskService taskService;

    @GetMapping("dialog/{taskId}/{action}")
    public String dialog(@PathVariable("taskId") String taskId, @PathVariable("action") String action, Model model) {

        try {
            model.addAttribute("dialogMessage",
                    model.getAttribute("dialogMessage") + Messages.HTML_BR +
                            dialogService.processTaskAction(action,taskId));
        } catch (Exception | DataException | ParameterException e) {
            model.addAttribute("dialogMessage",
                    model.getAttribute("dialogMessage") +
                            Messages.EXCEPTION_DIALOG_ACTION_TASK + Messages.SEPARATOR + e.getMessage());
            log.error((Messages.EXCEPTION_DIALOG_ACTION_TASK + Messages.SEPARATOR + e.getMessage()));
            e.printStackTrace();
            model.addAttribute("showDialog", true);
        }

        if(taskService.countActiveTasks() > 0) model.addAttribute("showDialog", true);

        return "redirect:/task/";

    }
}
