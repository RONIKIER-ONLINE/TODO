package online.ronikier.todo.infrastructure.storage;

import online.ronikier.todo.domain.File;
import online.ronikier.todo.domain.Task;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface StorageService {

	void init();

	void store(MultipartFile file, Long taskId) throws IOException;

	List<File> taskFiles(Long taskId);

	Iterable<File>  allFiles();

    Optional<File> findFileById(Long fileId);

//	Stream<Path> loadTaskFiles(Task task);

//	Path load(String filename, Long taskId);
//
//	Resource loadAsResource(String filename, Long taskId);

	public Resource loadAsResource(Long fileId);

}
