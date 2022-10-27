package online.ronikier.todo.interfaces.api;

import lombok.extern.slf4j.Slf4j;
import online.ronikier.todo.domain.Task;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class TaskAPIHandler {

    public Mono<ServerResponse> getTaskByName(ServerRequest request) {
        log.debug("Implement non-blocking task processing");
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(BodyInserters.fromValue(Task.builder().name("Turbo Task").build()));
    }

    public Mono<ServerResponse> updateTask(ServerRequest request) {
        log.debug("Implement non-blocking task processing");
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(BodyInserters.fromValue(Task.builder().name("Turbo Task").build()));
    }

    public Mono<ServerResponse> addTask(ServerRequest request) {
        log.debug("Implement non-blocking task processing");
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(BodyInserters.fromValue(Task.builder().name("Turbo Task").build()));
    }

}
