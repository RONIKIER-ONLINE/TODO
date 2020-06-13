package online.ronikier.todo;

import lombok.extern.slf4j.Slf4j;
import online.ronikier.todo.domain.Task;
import online.ronikier.todo.domain.dictionary.StateTask;
import online.ronikier.todo.domain.dictionary.TypeTask;
import online.ronikier.todo.infrastructure.repository.TaskRepository;
import online.ronikier.todo.library.Utilities;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

@Slf4j
@SpringBootApplication
@EnableNeo4jRepositories
public class Application {

    @Value("${todo.setup.initialize.database:false}")
    private boolean setupInitializeDatabase;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Profile("dev,google")
    @Bean
    CommandLineRunner initialize(TaskRepository taskRepository) {
        return args -> {

            if (setupInitializeDatabase) {
                log.info(Messages.INFO_INITIALISING_DATABASE);
                taskRepository.deleteAll();
            }

            Task millionDollarTask = new Task(null,null,true, true, Utilities.dateCurrent(), Utilities.dateCurrent(), Utilities.dateFuture(7), "Be Rich", "Million Dollars", StateTask.STARTED , TypeTask.MONEY, null);
            taskRepository.save(millionDollarTask);

        };
    }
}
