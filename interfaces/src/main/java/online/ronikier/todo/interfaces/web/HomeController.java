package online.ronikier.todo.interfaces.web;

import lombok.extern.slf4j.Slf4j;
import online.ronikier.todo.domain.Person;
import online.ronikier.todo.domain.forms.LoginForm;
import online.ronikier.todo.templete.SuperController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;

@Slf4j
@Controller
public class HomeController extends SuperController  {

    @Autowired
    private ApplicationContext applicationContext;

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

        applicationContext.getBean(RequestMappingHandlerMapping.class).getHandlerMethods()
                .forEach((requestMappingInfo, handlerMethod) -> {
                    log.info("requestMappingInfo:" + requestMappingInfo);
                    log.info("handlerMethod:" + handlerMethod);
                });

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

    @GetMapping("/secure")
    public String index(Principal principal) {
        return principal != null ? "/login" : "/task";
    }

}
