package online.ronikier.todo;

import lombok.extern.slf4j.Slf4j;
import online.ronikier.todo.domain.Task;
import online.ronikier.todo.infrastructure.TaskRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

import java.util.Arrays;
import java.util.List;

@Slf4j
@SpringBootApplication
@EnableNeo4jRepositories
public class TodoApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodoApplication.class, args);
		//System.out.println("chuj");
	}

	@Bean
	CommandLineRunner demo(TaskRepository taskRepository) {
		return args -> {

			taskRepository.deleteAll();

			Task millionDollarTask = new Task("Million Dollars");

			taskRepository.save(millionDollarTask);

		};
	}

}
