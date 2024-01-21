package online.ronikier.todo.interfaces.api;

import lombok.extern.slf4j.Slf4j;
import online.ronikier.todo.domain.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RestController
public class APIClient {

    @Autowired
    private RestTemplate restTemplate;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @GetMapping("test")

    public void test() {
        Task task = restTemplate.getForObject("http://localhost:9010/tasks/4", Task.class);

        log.info("CHUJ:" + task.getName());
    }


}
