package online.ronikier.todo.interfaces.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import online.ronikier.todo.Messages;
import online.ronikier.todo.domain.Brain;
import online.ronikier.todo.domain.exception.PersonNotValidatedException;
import online.ronikier.todo.domain.forms.LoginForm;
import online.ronikier.todo.infrastructure.service.PersonService;
import online.ronikier.todo.templete.SuperController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@Controller
public class HomeController extends SuperController  {

    private final Brain brain;

    private final PersonService personService;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
    }

    @GetMapping("/")
    public String login(LoginForm loginForm, Model model) {
        return "login";
    }

    @GetMapping("/logout")
    public String logout(LoginForm loginForm, Model model) {
        log.debug(Messages.DEBUG_MESSAGE_PREFIX + Messages.USER_LOGGED_OUT + Messages.SEPARATOR + brain.getLoggedPerson().getUsername());
        brain.setLoggedPerson(null);
        return "login";
    }

    @PostMapping("login")
    public String loginPerson(@Valid LoginForm loginForm, BindingResult bindingResult) {
        try {

            brain.setLoggedPerson(personService.retrievePerson(loginForm.getUsername(),loginForm.getPassword()));

            return "index";
        } catch (PersonNotValidatedException e) {
            log.warn(Messages.INFO_PERSON_NOT_FOUND);
            bindingResult.addError(new FieldError("loginForm","username",Messages.INFO_PERSON_NOT_FOUND));
        }
        return "login";
    }

}
