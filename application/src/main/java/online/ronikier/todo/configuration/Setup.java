package online.ronikier.todo.configuration;

import online.ronikier.todo.domain.Task;
import online.ronikier.todo.library.Utilities;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.*;

@Configuration
public class Setup {

    public static Set<Task> dafaultTasks() {
        Task initializationTask = new Task(true,true, Utilities.dateCurrent(), Utilities.dateCurrent(), Utilities.dateFuture(1),"Initialization","Initialization task");
        Task completionTask = new Task(true,true, Utilities.dateCurrent(), Utilities.dateCurrent(), Utilities.dateFuture(1),"Completion","Completion task");
        List<Task> dafaultTasks = Arrays.asList(initializationTask, completionTask);
        return new HashSet<>(dafaultTasks);
    }

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
        sessionLocaleResolver.setDefaultLocale(Locale.US);
        return sessionLocaleResolver;
    }


}
