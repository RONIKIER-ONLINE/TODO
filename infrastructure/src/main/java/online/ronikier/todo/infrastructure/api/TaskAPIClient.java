package online.ronikier.todo.infrastructure.api;

import org.springframework.stereotype.Component;

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
