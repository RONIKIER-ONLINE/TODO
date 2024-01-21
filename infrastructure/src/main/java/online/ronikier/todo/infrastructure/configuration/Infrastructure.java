package online.ronikier.todo.infrastructure.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class Infrastructure {

//    @Value("${todo.setup.neo4j.host:localhost}")
//    private String setupNeo4jHost;
//
//    @Value("${todo.setup.neo4j.port:7687}")
//    private String setupNeo4jPort;
//
//    @Value("${todo.setup.neo4j.username:neo4j}")
//    private String setupNeo4jUsername;
//
//    @Value("${todo.setup.neo4j.password:secret}")
//    private String setupNeo4jPassword;

//    @Bean
//    public org.neo4j.ogm.config.Configuration configuration() {
//        log.info(Messages.NEO4J_CONNECTION_SETUP);
//        return new org.neo4j.ogm.config.Configuration.Builder()
//                .uri("bolt://"+ setupNeo4jHost + ":" + setupNeo4jPort)
//                .credentials(setupNeo4jUsername, setupNeo4jPassword)
//                .build();
//    }

}
