package online.ronikier.todo.interfaces.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import online.ronikier.todo.Messages;
import online.ronikier.todo.domain.Person;
import online.ronikier.todo.domain.exception.PersonNotValidatedException;
import online.ronikier.todo.domain.forms.LoginForm;
import online.ronikier.todo.infrastructure.service.PersonInterface;
import online.ronikier.todo.library.UserSession;
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

    private final UserSession userSession;

    private final PersonInterface personService;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
    }

    @GetMapping("/")
    public String login(LoginForm loginForm, Model model) {
        return "login";
    }

    @GetMapping("/error")
    public String error() {
        log.error(Messages.DEBUG_MESSAGE_PREFIX + " - Cosik sie zjeballo ...");
        return "login";
    }

    @GetMapping("/logout")
    public String logout(LoginForm loginForm, Model model) {

        log.debug(Messages.DEBUG_MESSAGE_PREFIX + Messages.USER_LOGGED_OUT);

        personService.kill();

        return "login";
    }

    @PostMapping("login")
    public String loginPerson(@Valid LoginForm loginForm, BindingResult bindingResult) {

//        if (true) return "index";

        try {

            userSession.setPersonId(personService.retrievePerson(loginForm.getUsername(),loginForm.getPassword()).getId());
            return "index";

        } catch (PersonNotValidatedException e) {
            log.warn(Messages.INFO_PERSON_NOT_FOUND);
            bindingResult.addError(new FieldError("loginForm","username",Messages.INFO_PERSON_NOT_FOUND));
        }
        return "login";
    }

}
