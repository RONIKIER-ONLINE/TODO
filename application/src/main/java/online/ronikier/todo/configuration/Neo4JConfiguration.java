package online.ronikier.todo.configuration;

import online.ronikier.todo.domain.Person;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Neo4JConfiguration {

    @Value("${todo.setup.neo4j.host:localhost}")
    private String setupNeo4jHost;

    @Value("${todo.setup.neo4j.username:neo4j}")
    private String setupNeo4jUsername;

    @Value("${todo.setup.neo4j.password:secret}")
    private String setupNeo4jPassword;

    @Bean
    public org.neo4j.ogm.config.Configuration configuration() {
        return new org.neo4j.ogm.config.Configuration.Builder()
                .uri("bolt://"+ setupNeo4jHost + ":7687")
                .credentials(setupNeo4jUsername, setupNeo4jPassword)
                //.database("TODO_PROD")
                .build();
    }

}
