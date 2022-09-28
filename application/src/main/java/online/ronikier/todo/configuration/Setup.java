package online.ronikier.todo.configuration;

import online.ronikier.todo.Messages;
import online.ronikier.todo.domain.Task;
import online.ronikier.todo.domain.dictionary.CostUnit;
import online.ronikier.todo.domain.dictionary.TaskState;
import online.ronikier.todo.domain.dictionary.TaskStatus;
import online.ronikier.todo.domain.dictionary.TaskType;
import online.ronikier.todo.library.Utilities;

import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.boot.actuate.audit.AuditEventRepository;
import org.springframework.boot.actuate.audit.InMemoryAuditEventRepository;
import org.springframework.boot.actuate.trace.http.HttpTrace;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Instant;
import java.util.*;



@Configuration
public class Setup {

    @Bean
    public static Set<Task> dafaultTasks() {
        Task initializationTask = new Task(null,null,true, true, Utilities.dateCurrent(), Utilities.dateCurrent(), Utilities.dateFuture(1), "Initialization", "Initialization task", 1d, CostUnit.DAY, TaskState.NEW , TaskType.GENERAL, TaskStatus.OK);
        Task completionTask = new Task(null, null,true, true, Utilities.dateCurrent(), Utilities.dateCurrent(), Utilities.dateFuture(1), "Completion", "Completion task", 1d, CostUnit.DAY, TaskState.NEW ,TaskType.GENERAL, TaskStatus.OK);
        List<Task> dafaultTasks = Arrays.asList(initializationTask, completionTask);
        return new HashSet<>(dafaultTasks);
    }

    @Bean
    public static Task devTask() {
        return new Task(null,null,true, true, Utilities.dateCurrent(), Utilities.dateCurrent(), Utilities.dateFuture(1), "DEV_TEST", "Test development task", 0d, CostUnit.SOLDIER, TaskState.NEW ,TaskType.GENERAL, TaskStatus.OK);
    }

    @Bean
    public AuditEventRepository auditEventRepository() {
        //TODO: If needed return customAuditEventRepository(); ???
        return new InMemoryAuditEventRepository();
    }

    @Bean
    public HttpTraceRepository httpTraceRepository() {
        return new HttpTraceRepository() {
            private List<HttpTrace> traceList = new ArrayList<>();
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
                throw new UnsupportedOperationException(Messages.DEV_IMPLEMENT_ME);
            }

            @Override
            public List<AuditEvent> find(String principal, Instant after, String type) {
                throw new UnsupportedOperationException(Messages.DEV_IMPLEMENT_ME);
            }
        };
    }
}
