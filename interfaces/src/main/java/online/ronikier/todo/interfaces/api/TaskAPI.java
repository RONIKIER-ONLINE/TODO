package online.ronikier.todo.interfaces.api;

import online.ronikier.todo.domain.Task;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "tasks", path = "tasks")
public interface TaskAPI extends PagingAndSortingRepository<Task, Long> {

	List<Task> findByName(@Param("taskName") String taskName);

}
