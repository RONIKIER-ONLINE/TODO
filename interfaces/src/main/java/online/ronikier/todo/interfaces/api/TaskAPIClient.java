package online.ronikier.todo.interfaces.api;

import online.ronikier.todo.domain.Task;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class TaskAPIClient {

    /*

    private final WebClient client;

    public TaskAPIClient(WebClient.Builder builder) {
        this.client = builder.baseUrl("http://localhost:9010").build();
    }

    public Mono<String> getTaskSatanas() {
        return this.client.get().uri("/api/task/id/666").accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Task.class)
                .map(Task::getName);
    }

     */

}
