package online.ronikier.todo.interfaces.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import online.ronikier.todo.domain.File;
import online.ronikier.todo.domain.Task;
import online.ronikier.todo.domain.forms.TaskForm;
import online.ronikier.todo.infrastructure.service.TaskService;
import online.ronikier.todo.infrastructure.storage.StorageService;
import online.ronikier.todo.templete.SuperController;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
public class FileController extends SuperController {

	private final StorageService storageService;

    @GetMapping(value = "file/{fileId}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable Long fileId) {

        Optional<File> optionalFile =  storageService.findFileById(fileId);
        if (optionalFile.isPresent()) {
            log.info("FX:>>>" + optionalFile.get().getName());
        }

        Resource file = storageService.loadAsResource(fileId);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

}
