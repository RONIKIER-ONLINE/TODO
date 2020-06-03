package online.ronikier.todo;

import lombok.extern.slf4j.Slf4j;
import online.ronikier.todo.domain.Task;
import online.ronikier.todo.infrastructure.TaskRepository;
import online.ronikier.todo.library.Utilities;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.boot.actuate.audit.AuditEventRepository;
import org.springframework.boot.actuate.audit.InMemoryAuditEventRepository;
import org.springframework.boot.actuate.trace.http.HttpTrace;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Slf4j
@SpringBootApplication
@EnableNeo4jRepositories
public class Application {

    @Value("${todo.setup.initialize.database:false}")
    private boolean setupInitializeDatabase;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner initialize(TaskRepository taskRepository) {
        return args -> {

            if (setupInitializeDatabase) {
                log.info(Messages.INFO_INITIALISING_DATABASE);
                taskRepository.deleteAll();
                Task millionDollarTask = new Task(null,true, true, Utilities.dateCurrent(), Utilities.dateCurrent(), Utilities.dateFuture(7), "Be Rich", "Million Dollars");
                taskRepository.save(millionDollarTask);

            }
        };
    }
}
