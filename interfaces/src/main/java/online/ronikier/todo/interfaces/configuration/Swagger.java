package online.ronikier.todo.interfaces.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class Swagger implements WebMvcConfigurer {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());

    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "RONIKIER.ONLINE TODO REST API",
                "REST interface to TODO system",
                "0.0.1",
                "https://www.linkedin.com/in/ronikier/",
                new Contact("Lou Ronikier", "http://ronikier.online/", "lukasz657@gmail.com"),
                "GNU GENERAL PUBLIC LICENSE", "https://www.gnu.org/licenses/gpl-3.0.html", Collections.emptyList());
    }

}
