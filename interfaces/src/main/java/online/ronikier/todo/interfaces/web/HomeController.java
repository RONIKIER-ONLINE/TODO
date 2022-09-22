package online.ronikier.todo.interfaces.web;

import online.ronikier.todo.domain.Person;
import online.ronikier.todo.domain.forms.LoginForm;
import online.ronikier.todo.domain.forms.PersonForm;
import online.ronikier.todo.library.Parameters;
import online.ronikier.todo.templete.SuperController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;

import javax.validation.Valid;

@Controller
public class HomeController extends SuperController  {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
    }

    @GetMapping("lou")
    public @ResponseBody
    String index() {
        return "Lou rulezzezzzz";
    }

    @GetMapping("/")
    public String login(LoginForm loginForm, Model model) {
        return "login";
    }

    @PostMapping("login")
    public String loginPerson(@Valid LoginForm loginForm, BindingResult bindingResult, Model model) {

        Person person = new Person();
        person.setUsername(loginForm.getUsername());
        if (loginForm.getPassword().equals("aaaaaa")) return "index";
        model.addAttribute("person",person);
        return "login";
    }

}
