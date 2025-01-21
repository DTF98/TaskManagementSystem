package ru.DTF98.taskManagementSystem.comment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.DTF98.taskManagementSystem.auth.model.User;
import ru.DTF98.taskManagementSystem.auth.service.UserService;
import ru.DTF98.taskManagementSystem.comment.dto.AddCommentDTO;
import ru.DTF98.taskManagementSystem.comment.mapper.CommentMapper;
import ru.DTF98.taskManagementSystem.comment.model.Comment;
import ru.DTF98.taskManagementSystem.comment.model.CommentDTO;
import ru.DTF98.taskManagementSystem.comment.repository.CommentRepository;
import ru.DTF98.taskManagementSystem.exception.NotEnoughRightsForChanges;
import ru.DTF98.taskManagementSystem.exception.NotFoundTaskException;
import ru.DTF98.taskManagementSystem.task.model.Task;
import ru.DTF98.taskManagementSystem.task.repository.TaskRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CommentService {
    private final TaskRepository taskRepository;
    private final CommentRepository commentRepository;
    private final UserService userService;
    private final CommentMapper commentMapper;

    /**
     * Создание комментария пользователем с проверкой его прав
     *
     * @param inputDto DTO данные комментария
     * @return CommentDTO
     */
    public CommentDTO createByUser(AddCommentDTO inputDto) {
        Task  task = getTaskInternal(inputDto.getTaskId());
        User requesterUser = userService.getCurrentUser();

        if (!task.getAssignee().getUserId().equals(requesterUser.getUserId())) {
            throw new NotEnoughRightsForChanges(
                    String.format("Пользователь с id = %d не может создавать комментарии для задачи с id = %d",
                            requesterUser.getUserId(), task.getAssignee().getUserId()));
        }

        Comment newComment = commentMapper.toModel(inputDto.getComment(), requesterUser, task);
        newComment = commentRepository.save(newComment);
        log.info("Добавлен комментарий {}", newComment);

        return commentMapper.toDTO(newComment);
    }

    /**
     * Создание комментария администратором
     *
     * @param inputDto DTO данные комментария
     * @return CommentDTO
     */
    public CommentDTO createByAdmin(AddCommentDTO inputDto) {
        Task  task = getTaskInternal(inputDto.getTaskId());
        User requesterUser = userService.getCurrentUser();

        Comment newComment = commentMapper.toModel(inputDto.getComment(), requesterUser, task);
        newComment = commentRepository.save(newComment);
        log.info("Добавлен комментарий {}", newComment);

        return commentMapper.toDTO(newComment);
    }

    /**
     * Получение комментариев к задаче с пагинацией по id задачи
     *
     * @param taskId id задачи
     * @param pageRequest - данные о пагинации
     * @return List<CommentDTO>
     */
    @Transactional(readOnly = true)
    public List<CommentDTO> getListByTaskId(Long taskId, PageRequest pageRequest) {
        List<Comment> comments = commentRepository.findByTaskId(taskId, pageRequest);
        log.info("Получен список комментариев {} к задаче id = {}", comments, taskId);
        return commentMapper.toListDTO(comments);
    }

    /**
     * Получение комментариев к задаче по id задачи
     *
     * @param taskId id задачи
     * @return List<CommentDTO>
     */
    @Transactional(readOnly = true)
    public List<CommentDTO> getListByTaskId(Long taskId) {
        List<Comment> comments = commentRepository.findByTaskId(taskId);
        log.info("Получен список комментариев {} к задаче id = {}", comments, taskId);
        return commentMapper.toListDTO(comments);
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
                () -> new NotFoundTaskException(String.format("Задача по id=%d не найдена.", taskId)));
    }
}
