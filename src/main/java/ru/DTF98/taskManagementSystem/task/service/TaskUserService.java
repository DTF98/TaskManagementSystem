package ru.DTF98.taskManagementSystem.task.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.DTF98.taskManagementSystem.auth.model.User;
import ru.DTF98.taskManagementSystem.auth.service.UserService;
import ru.DTF98.taskManagementSystem.comment.model.CommentDTO;
import ru.DTF98.taskManagementSystem.comment.service.CommentService;
import ru.DTF98.taskManagementSystem.exception.NotEnoughRightsForChanges;
import ru.DTF98.taskManagementSystem.exception.NotFoundTaskException;
import ru.DTF98.taskManagementSystem.task.dto.TaskDTO;
import ru.DTF98.taskManagementSystem.task.dto.TaskWithCommentsDTO;
import ru.DTF98.taskManagementSystem.task.dto.UpdateUserTaskDTO;
import ru.DTF98.taskManagementSystem.task.mapper.TaskMapper;
import ru.DTF98.taskManagementSystem.task.model.Task;
import ru.DTF98.taskManagementSystem.task.repository.TaskRepository;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class TaskUserService {
    private final TaskRepository taskRepository;
    private final UserService userService;
    private final TaskMapper taskMapper;
    private final CommentService commentService;

    /**
     * Обновление статуса задачи
     *
     * @param inputDto DTO данные о задаче
     * @return TaskDTO
     */
    public TaskDTO updateStatus(UpdateUserTaskDTO inputDto) {
        Task oldTask = getTaskInternal(inputDto.getTaskId());
        User requesterUser = userService.getCurrentUser();

        if (!oldTask.getAssignee().getUserId().equals(requesterUser.getUserId())) {
            throw new NotEnoughRightsForChanges(
                    String.format("Пользователь с id = %d не может редактировать задачу с id = %d",
                            requesterUser.getUserId(), oldTask.getAssignee().getUserId()));
        }

        oldTask.setStatus(inputDto.getStatus());
        Task updateTask = taskRepository.save(oldTask);
        log.info("Обновлена задача {}", updateTask);

        return taskMapper.toDTO(updateTask);
    }

    /**
     * Получение задачи по id вместе с комментариями
     *
     * @param id id задачи
     * @return TaskWithCommentsDTO
     */
    public TaskWithCommentsDTO getById(Long id) {
        Task task = getTaskInternal(id);
        User requesterUser = userService.getCurrentUser();

        if (!task.getAssignee().getUserId().equals(requesterUser.getUserId())) {
            throw new NotEnoughRightsForChanges(
                    String.format("Пользователь с id = %d не может редактировать задачу с id = %d",
                            requesterUser.getUserId(), task.getAssignee().getUserId()));
        }

        log.info("Получена задача {}", task);
        List<CommentDTO> comments = commentService.getListByTaskId(id);
        return taskMapper.toDTOWithComments(task, comments);
    }

    /**
     * Получение задачи по id для внутренней работы сервиса
     *
     * @param taskId id задачи
     * @return Task
     */
    @Transactional(readOnly = true)
    private Task getTaskInternal(Long taskId) {
        return taskRepository.findById(taskId).orElseThrow(
                () -> new NotFoundTaskException(String.format("Task with id=%d not found.", taskId)));
    }
}
