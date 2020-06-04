package online.ronikier.todo.interfaces.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Neo4JConfiguration {
    @Bean
    public org.neo4j.ogm.config.Configuration configuration() {
        org.neo4j.ogm.config.Configuration configuration = new org.neo4j.ogm.config.Configuration.Builder()
                .uri("bolt://localhost:7687")
                .credentials("neo4j", "secret")
//                .uri("bolt://35.202.136.199:7687")
//                .credentials("neo4j", "M51E46pFsu")
                .build();
        return configuration;
    }
}
