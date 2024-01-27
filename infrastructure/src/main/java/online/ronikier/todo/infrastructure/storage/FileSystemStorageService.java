package online.ronikier.todo.infrastructure.storage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import online.ronikier.todo.Messages;
import online.ronikier.todo.domain.File;
import online.ronikier.todo.domain.Task;
import online.ronikier.todo.infrastructure.repository.FileRepository;
import online.ronikier.todo.infrastructure.service.TaskService;
import online.ronikier.todo.library.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import java.util.Optional;

/**
 *
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FileSystemStorageService implements StorageService {

	private final FileRepository fileRepository;

	private final TaskService taskService;

	@Value("${todo.setup.storage.location}")
	private String setupStorageLocation;

	@Override
	public void store(MultipartFile uploadedFile, Long taskId) throws IOException {

		Task task = taskService.findTaskById(taskId)
				.orElseThrow(() -> new StorageException(Messages.TASK_NOT_FOUND + Messages.SEPARATOR + taskId));

		if (uploadedFile.isEmpty()) {
			throw new StorageException(Messages.FILE_STORE_FAILED + Messages.SEPARATOR + uploadedFile.getOriginalFilename());
		}

		List<online.ronikier.todo.domain.File> fileList = fileRepository
				.findByName(uploadedFile.getName());
				//.stream()
				//.filter(file -> file.getTask().getId() == taskId)
				//.collect(Collectors.toList());

		if (!fileList.isEmpty())
					throw new StorageException(Messages.FILE_STORE_FAILED);

			online.ronikier.todo.domain.File file = new File();

			file.setName(uploadedFile.getOriginalFilename());
			file.setTask(task);
			file.setContentType(uploadedFile.getContentType());
			file.setSize(uploadedFile.getSize());
			file.setUploadDate(Utilities.dateCurrent());

			fileRepository.save(file);

			Files.copy(uploadedFile.getInputStream(), Paths.get(setupStorageLocation + "/" + task.getId()).resolve(uploadedFile.getOriginalFilename()));

	}

	@Override
	public List<online.ronikier.todo.domain.File> taskFiles(Long taskId) {
		return fileRepository.findByTaskId(taskId);
	}

	@Override
	public Iterable<online.ronikier.todo.domain.File> allFiles() {
		return fileRepository.findAll();
	}

	@Override
	public Optional<File> findFileById(Long fileId) {
		return fileRepository.findById(fileId);
	}

	@Override
	public Resource loadAsResource(Long fileId) {

		File fileDB = fileRepository.findById(fileId).get();

		try {
			Path file = Paths.get(setupStorageLocation + "/" + fileDB.getTask().getId() + "/" + fileDB.getName());
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			}
			else {
				throw new StorageFileNotFoundException("JEBNELO 1");

			}
		}
		catch (MalformedURLException e) {
			throw new StorageFileNotFoundException("JEBNELO 2");
		}
	}

	@Override
	public void init() {
		fileRepository.deleteAll();
		Utilities.initFileDirectory(setupStorageLocation);
	}


}
