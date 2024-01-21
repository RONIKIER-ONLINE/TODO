package online.ronikier.todo.infrastructure.service;

import online.ronikier.todo.domain.exception.DataException;
import online.ronikier.todo.domain.exception.ParameterException;

public interface DialogService {
    String processTaskAction(String action, String taskId) throws DataException, ParameterException;
}
