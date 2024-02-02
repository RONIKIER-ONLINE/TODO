package online.ronikier.todo.configuration;

import lombok.extern.slf4j.Slf4j;
import online.ronikier.todo.Messages;
import online.ronikier.todo.domain.Task;
import online.ronikier.todo.domain.dictionary.CostUnit;
import online.ronikier.todo.domain.dictionary.TaskState;
import online.ronikier.todo.domain.dictionary.TaskStatus;
import online.ronikier.todo.domain.dictionary.TaskType;
import online.ronikier.todo.library.Utilities;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.boot.actuate.audit.AuditEventRepository;
import org.springframework.boot.actuate.trace.http.HttpTrace;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templateresolver.UrlTemplateResolver;

import java.time.Instant;
import java.util.*;


@Slf4j
@Configuration
public class Setup {

//    @Bean
//    public SpringTemplateEngine templateEngine() {
//        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
//        templateEngine.addTemplateResolver(new UrlTemplateResolver());
//    //...
//        return templateEngine;
//    }

    @Bean
    public Set<Task> dafaultTasks() {
        Task initializationTask = new Task(null,null,null,null,null,true, true, Utilities.dateMorning(), Utilities.dateMorning(), Utilities.dateFuture(1), null, "Initialization", "Initialization task", 1d, CostUnit.DAY, TaskState.STARTED , TaskType.GENERAL, TaskStatus.OK,null);
        Task completionTask = new Task(null,null,null,null,null,true, true, Utilities.dateMorning(), Utilities.dateMorning(), Utilities.dateFuture(1),null, "Completion", "Completion task", 1d, CostUnit.DAY, TaskState.ON_HOLD ,TaskType.GENERAL, TaskStatus.OK,null);
        List<Task> dafaultTasks = Arrays.asList(initializationTask, completionTask);
        return new HashSet<>(dafaultTasks);
    }

    @Bean
    public Task devTask() {
        return new Task(null,null,null,null,null,null,true, Utilities.dateMorning(), Utilities.dateMorning(), Utilities.dateFuture(1), null, "DEV_TEST", "Test development task", 0d, CostUnit.SOLDIER, TaskState.ON_HOLD ,TaskType.GENERAL, TaskStatus.OK, null);
    }

    @Bean
    public AuditEventRepository auditEventRepository() {
        return customAuditEventRepository();
    }

    @Bean
    public HttpTraceRepository httpTraceRepository() {
        return new HttpTraceRepository() {
            private final List<HttpTrace> traceList = new ArrayList<>();
            @Override
            public List<HttpTrace> findAll() {
                return traceList;
            }

            @Override
            public void add(HttpTrace trace) {
                traceList.add(trace);
            }
        };
    }

    private AuditEventRepository customAuditEventRepository() {
        return new AuditEventRepository() {

            @Override
            public void add(AuditEvent event) {
                log.debug(Messages.DEV_IMPLEMENT_ME);
                throw new UnsupportedOperationException(Messages.DEV_IMPLEMENT_ME);
            }

            @Override
            public List<AuditEvent> find(String principal, Instant after, String type) {
                log.debug(Messages.DEV_IMPLEMENT_ME);
                return Collections.emptyList();
            }
        };
    }
}
