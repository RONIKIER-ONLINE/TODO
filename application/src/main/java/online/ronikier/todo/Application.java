package online.ronikier.todo;

import lombok.extern.slf4j.Slf4j;
import online.ronikier.todo.domain.Person;
import online.ronikier.todo.domain.Task;
import online.ronikier.todo.domain.dictionary.CostUnit;
import online.ronikier.todo.domain.dictionary.TaskState;
import online.ronikier.todo.domain.dictionary.TaskStatus;
import online.ronikier.todo.domain.dictionary.TaskType;
import online.ronikier.todo.domain.exception.PersonNotFoundException;
import online.ronikier.todo.infrastructure.repository.TaskRepository;
import online.ronikier.todo.infrastructure.service.PersonService;
import online.ronikier.todo.infrastructure.service.TaskService;
import online.ronikier.todo.infrastructure.storage.StorageProperties;
import online.ronikier.todo.infrastructure.storage.StorageService;
import online.ronikier.todo.library.UserSession;
import online.ronikier.todo.library.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.context.annotation.SessionScope;

@Slf4j
@SpringBootApplication
@EnableNeo4jRepositories
@EnableScheduling
@EnableConfigurationProperties(StorageProperties.class)
public class Application {

    @Value("${todo.setup.initialize.database:false}")
    private boolean setupInitializeDatabase;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    @SessionScope
    public UserSession userSession() {
        return new UserSession();
    }

}
