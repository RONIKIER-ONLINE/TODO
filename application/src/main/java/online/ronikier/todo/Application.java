package online.ronikier.todo;

import lombok.extern.slf4j.Slf4j;
import online.ronikier.todo.domain.Task;
import online.ronikier.todo.infrastructure.TaskRepository;
import online.ronikier.todo.library.Utilities;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

@Slf4j
@SpringBootApplication
@EnableNeo4jRepositories
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner initialize(TaskRepository taskRepository) {
        return args -> {

            taskRepository.deleteAll();
            Task millionDollarTask = new Task(true, true, Utilities.dateCurrent(), Utilities.dateCurrent(), Utilities.dateFuture(7), "Be Rich", "Million Dollars");
            taskRepository.save(millionDollarTask);

        };
    }

}