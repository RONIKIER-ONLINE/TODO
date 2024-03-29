package online.ronikier.todo;

import lombok.extern.slf4j.Slf4j;
import online.ronikier.todo.domain.Task;
import online.ronikier.todo.domain.dictionary.CostUnit;
import online.ronikier.todo.domain.dictionary.TaskState;
import online.ronikier.todo.domain.dictionary.TaskStatus;
import online.ronikier.todo.domain.dictionary.TaskType;
import online.ronikier.todo.infrastructure.repository.TaskRepository;
import online.ronikier.todo.infrastructure.storage.StorageService;
import online.ronikier.todo.library.Utilities;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Arrays;

@Slf4j
@SpringBootApplication
//@EnableNeo4jRepositories
@EnableScheduling
public class Application {

    @Value("${todo.setup.initialize.database:0}")
    private boolean setupInitializeDatabase;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    //@Profile("dev,google")
    @Bean
    CommandLineRunner initialize(TaskRepository taskRepository, StorageService storageService) {
        return args -> {

            if (setupInitializeDatabase) {
                log.info(Messages.INITIALISING_DATABASE);
                //taskRepository.deleteAll();
            }

            //Task millionDollarTask = new Task(null,null,null,null,null,true, true, Utilities.dateMorning(), Utilities.dateMorning(), Utilities.dateFuture(7),null, "Be Rich", "Million Dollars", 1000000d, CostUnit.SOLDIER, TaskState.STARTED , TaskType.MONEY, TaskStatus.OK);
            //taskRepository.save(millionDollarTask);

//            storageService.init();

            logLabels();

        };
    }

    public static void logLabels() {
        log.info(Messages.LONG_RULE);
        Arrays.stream(TaskState.values()).forEach(taskState -> {
            log.info("TaskState:" + taskState);
        });
        log.info(Messages.LONG_RULE);
        log.info(Messages.ERROR_PARAMETER_NAME_EMPTY);
        Arrays.stream(TaskStatus.values()).forEach(taskStatus -> {
            log.info("TaskStatus:" + taskStatus);
        });
        log.info(Messages.LONG_RULE);
    }

}
