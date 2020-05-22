package online.ronikier.todo;

import lombok.extern.slf4j.Slf4j;
import online.ronikier.todo.config.Parameters;
import online.ronikier.todo.domain.Task;
import online.ronikier.todo.infrastructure.TaskRepository;
import online.ronikier.todo.library.Utilities;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cache.support.NullValue;
import org.springframework.context.annotation.Bean;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Slf4j
@SpringBootApplication
@EnableNeo4jRepositories
public class TodoApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodoApplication.class, args);
	}

	@Bean
	CommandLineRunner initialize(TaskRepository taskRepository) {
		return args -> {

			taskRepository.deleteAll();
			Task millionDollarTask = new Task(true, true, Utilities.dateCurrent(), Utilities.dateFuture(7),"Be Rich","Million Dollars", null);
			taskRepository.save(millionDollarTask);

		};
	}

}
