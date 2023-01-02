package online.ronikier.todo.infrastructure.api;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import online.ronikier.todo.domain.Task;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;


@OpenAPIDefinition(info = @Info(
        contact = @Contact(
                email = "lukasz657@gmail.com",
                name = "L.Ronikier"),
        description = "TODO system REST interface",
        title = "RONIKIER.ONLINE TODO REST API",
        version = "1.0.1"
))
@Component
public class TaskAPIRouter {

    @RouterOperations(
            {
                    @RouterOperation(
                            path = "/turbo/task/name/{name}",
                            produces = {
                                    MediaType.APPLICATION_JSON_VALUE
                            },
                            method = RequestMethod.GET,
                            beanClass = TaskAPIHandler.class,
                            beanMethod = "getTaskByName",
                            operation = @Operation(
                                    operationId = "getTaskByName",
                                    responses = {
                                            @ApiResponse(
                                                    responseCode = "200",
                                                    description = "Here is ur task",
                                                    content = @Content(schema = @Schema(
                                                            implementation = Task.class
                                                    ))
                                            ),
                                            @ApiResponse(
                                                    responseCode = "404",
                                                    description = "Task not found"
                                            )
                                    },
                                    parameters = {
                                            @Parameter(in = ParameterIn.PATH, name = "name")
                                    }
                            )
                    )}
    )

    public RouterFunction<ServerResponse> route(TaskAPIHandler taskAPIHandler) {

        return RouterFunctions.route()
                .GET("/turbo/task/name/{name}", RequestPredicates.queryParam("name", t -> true), taskAPIHandler::getTaskByName)
                .build();

    }
}
