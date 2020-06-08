package online.ronikier.todo.interfaces.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

    @GetMapping("/home")
    public @ResponseBody
    String index() {
        return "Lou rulezzezzzz";
    }

}
