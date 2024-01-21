package online.ronikier.todo.interfaces.web;

import lombok.RequiredArgsConstructor;
import online.ronikier.todo.domain.forms.TaskForm;
import online.ronikier.todo.infrastructure.service.TaskService;
import online.ronikier.todo.infrastructure.storage.StorageFileNotFoundException;
import online.ronikier.todo.infrastructure.storage.StorageService;
import online.ronikier.todo.templete.SuperController;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.stream.Collectors;

//@Controller
@RequiredArgsConstructor
public class FileUploadController extends SuperController {

	private final StorageService storageService;
	private final TaskService taskService;

	@GetMapping(value = "uploadForm", produces = "text/html")
	public String uploadForm(TaskForm taskForm, Model model) {
		if (!securityCheckOK(model)) return "login";
		refreshForm(taskForm, model);
		if (taskForm.getTask().getId() == null) {
			taskForm.setTask(taskService.initializeTask());
		}
		return "task";
	}

	@GetMapping("/file")
	public String listUploadedFiles(Model model) throws IOException {

		model.addAttribute("files", storageService.loadAll().map(
				path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
						"serveFile", path.getFileName().toString()).build().toUri().toString())
				.collect(Collectors.toList()));

		return "uploadForm";
	}

	@GetMapping("/files/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

		Resource file = storageService.loadAsResource(filename);
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
				"attachment; filename=\"" + file.getFilename() + "\"").body(file);
	}

	@PostMapping("/file")
	public String handleFileUpload(@RequestParam("file") MultipartFile file,
			RedirectAttributes redirectAttributes) {

		storageService.store(file);
		redirectAttributes.addFlashAttribute("message",
				"You successfully uploaded " + file.getOriginalFilename() + "!");

		return "redirect:/file";
	}

	@ExceptionHandler(StorageFileNotFoundException.class)
	public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
		return ResponseEntity.notFound().build();
	}

}
