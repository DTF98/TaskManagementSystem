package ru.DTF98.taskManagementSystem.task.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.DTF98.taskManagementSystem.auth.model.User;
import ru.DTF98.taskManagementSystem.auth.service.UserService;
import ru.DTF98.taskManagementSystem.comment.model.CommentDTO;
import ru.DTF98.taskManagementSystem.comment.service.CommentService;
import ru.DTF98.taskManagementSystem.exception.NotFoundTaskException;
import ru.DTF98.taskManagementSystem.task.dto.CreateTaskDTO;
import ru.DTF98.taskManagementSystem.task.dto.TaskDTO;
import ru.DTF98.taskManagementSystem.task.dto.TaskWithCommentsDTO;
import ru.DTF98.taskManagementSystem.task.dto.UpdateAdminTaskDTO;
import ru.DTF98.taskManagementSystem.task.mapper.TaskMapper;
import ru.DTF98.taskManagementSystem.task.model.Status;
import ru.DTF98.taskManagementSystem.task.model.Task;
import ru.DTF98.taskManagementSystem.task.repository.TaskRepository;

import java.util.AbstractSequentialList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class TaskAdminService {
    private final TaskRepository taskRepository;
    private final UserService userService;
    private final TaskMapper taskMapper;
    private final CommentService commentService;

    /**
     * Создание задачи
     *
     * @param createTaskDTO DTO данные о задаче
     * @return TaskDTO
     */
    public TaskDTO create(CreateTaskDTO createTaskDTO) {
        User author = userService.getCurrentUser();
        User assignee = userService.getById(createTaskDTO.getAssignee().getUserId());

        Task createdTask = taskMapper.toModel(createTaskDTO, author, Status.IN_WAITING);
        createdTask = taskRepository.save(createdTask);
        log.info("Создана задача {}", createdTask);

        TaskDTO response = taskMapper.toDTO(createdTask);
        response.setAssignee(taskMapper.toUserDTO(assignee));
        return response;
    }

    /**
     * Обновление задачи
     *
     * @param updateAdminTaskDTO обновленные DTO данные о задаче
     * @return TaskDTO
     */
    public TaskDTO update(UpdateAdminTaskDTO updateAdminTaskDTO) {
        Task oldTask = getTaskInternal(updateAdminTaskDTO.getTaskId());

        taskMapper.updateModel(oldTask, updateAdminTaskDTO);

        User assignee;
        if (updateAdminTaskDTO.getAssignee() != null) {
            assignee = userService.getById(updateAdminTaskDTO.getAssignee().getUserId());
        } else {
            assignee = oldTask.getAssignee();
        }
        oldTask.setAssignee(assignee);
        Task updatedTask = taskRepository.save(oldTask);
        log.info("Обновлена задача {}", updatedTask);

        return taskMapper.toDTO(updatedTask);
    }

    /**
     * Получение задачи по id
     *
     * @param taskId id задачи
     * @return TaskWithCommentsDTO
     */
    @Transactional(readOnly = true)
    public TaskWithCommentsDTO getById(Long taskId) {
        Task task = getTaskInternal(taskId);
        log.info("Получена задача {}", task);
        List<CommentDTO> commentDTOS = commentService.getListByTaskId(taskId);
        return taskMapper.toDTOWithComments(task, commentDTOS);
    }

    /**
     * Получение задач автора с комментариями и пагинацией
     * @param authorId id автора задачи
     * @param page номер первой страницы(начинаются с 0)
     * @param size колличество элементов на странице
     * @return List<TaskWithCommentsDTO>
     */
    @Transactional(readOnly = true)
    public List<TaskWithCommentsDTO> getListByAuthorId(Long authorId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        List<Task> tasks = taskRepository.findByAuthorId(authorId, pageRequest).getContent();
        if (tasks.isEmpty()) {
            log.info("Список задач для автора id = {} пуст", authorId);
            return List.of();
        }
        List<TaskWithCommentsDTO> listOfDTO = tasks.stream()
                .map(task -> getDTOWithCommentsByTask(task, pageRequest))
                .toList();
        log.info("Получен список задач {}, для автора id = {}", listOfDTO, authorId);
        return listOfDTO;
    }

    /**
     * Получение задач исполнителя с комментариями и пагинацией
     * @param assigneeId id исполнителя задачи
     * @param page номер первой страницы(начинаются с 0)
     * @param size колличество элементов на странице
     * @return List<TaskWithCommentsDTO>
     */
    @Transactional(readOnly = true)
    public List<TaskWithCommentsDTO> getListByAssigneeId(Long assigneeId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        List<Task> tasks = taskRepository.findByAssigneeId(assigneeId, pageRequest).getContent();
        if (tasks.isEmpty()) {
            log.info("Список задач для исполнителя id = {} пуст", assigneeId);
            return List.of();
        }
        List<TaskWithCommentsDTO> listOfDTO = tasks.stream()
                .map(task -> getDTOWithCommentsByTask(task, pageRequest))
                .toList();
        log.info("Получен список задач {}, для исполнителя id = {}", listOfDTO, assigneeId);
        return listOfDTO;
    }

    /**
     * Удаление задачи по id
     *
     * @param id задачи
     */
    public void delete(Long id) {
        Task deleteTask = getTaskInternal(id);
        taskRepository.deleteById(id);
        log.info("Задача удалена {}", deleteTask);
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
                () -> new NotFoundTaskException(String.format("Задача с id=%d не найдена.", taskId)));
    }

    /**
     * Получение DTO задачи с комментарием и пагинацией, нужно для внуутренней работы приложения
     *
     * @param task данные о задаче
     * @param pageRequest данные о пагинации
     * @return TaskWithCommentsDTO
     */
    private TaskWithCommentsDTO getDTOWithCommentsByTask(Task task, PageRequest pageRequest) {
        List<CommentDTO> comments = commentService.getListByTaskId(task.getTaskId(), pageRequest);
        return taskMapper.toDTOWithComments(task, comments);
    }
}
