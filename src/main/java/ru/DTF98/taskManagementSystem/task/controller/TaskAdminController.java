package ru.DTF98.taskManagementSystem.task.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.DTF98.taskManagementSystem.comment.dto.AddCommentDTO;
import ru.DTF98.taskManagementSystem.comment.model.CommentDTO;
import ru.DTF98.taskManagementSystem.comment.service.CommentService;
import ru.DTF98.taskManagementSystem.task.dto.CreateTaskDTO;
import ru.DTF98.taskManagementSystem.task.dto.TaskDTO;
import ru.DTF98.taskManagementSystem.task.dto.TaskWithCommentsDTO;
import ru.DTF98.taskManagementSystem.task.dto.UpdateAdminTaskDTO;
import ru.DTF98.taskManagementSystem.task.service.TaskAdminService;

import java.util.List;

@RestController
@RequestMapping("/admin/resource")
@RequiredArgsConstructor
@Tag(name = "Контролер для Администраторов")
@Slf4j
@Validated
public class TaskAdminController {
    private final TaskAdminService taskAdminService;
    private final CommentService commentService;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Получить задачу, если пользователь является админимстратором")
    @PreAuthorize("hasRole('ADMIN')")
    public TaskWithCommentsDTO getById(@PathVariable Long id) {
        return taskAdminService.getById(id);
    }

    @GetMapping("/author")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Получить задачи конкретного автора, если пользователь является админимстратором")
    @PreAuthorize("hasRole('ADMIN')")
    public List<TaskWithCommentsDTO> getListByAuthorId(@RequestParam @Min(0) int page,
                                       @RequestParam @Positive int size,
                                       @RequestHeader("X-Author-Id") Long authorId) {
        return taskAdminService.getListByAuthorId(authorId, page, size);
    }

    @GetMapping("/assignee")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Получить задачи конкретного исполнителя, если пользователь является админимстратором")
    @PreAuthorize("hasRole('ADMIN')")
    public List<TaskWithCommentsDTO> getListByAssigneeId(@RequestParam @Min(0) int page,
                                             @RequestParam @Positive int size,
                                             @RequestHeader("X-Assignee-Id") Long assigneeId) {
        return taskAdminService.getListByAssigneeId(assigneeId, page, size);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Обновить задачу, если пользователь является админимстратором")
    @PreAuthorize("hasRole('ADMIN')")
    public TaskDTO update(@RequestBody @Valid UpdateAdminTaskDTO inputDto) {
        return taskAdminService.update(inputDto);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Создать задачу, если пользователь является админимстратором")
    @PreAuthorize("hasRole('ADMIN')")
    public TaskDTO create(@RequestBody @Valid CreateTaskDTO inputDto) {
        return taskAdminService.create(inputDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Удалить задачу, если пользователь является админимстратором")
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable Long id) {
        taskAdminService.delete(id);
    }

    @PutMapping("/comment")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Добавить комментарий к задаче, если пользователь является админимстратором")
    @PreAuthorize("hasRole('ADMIN')")
    public CommentDTO createComment(@RequestBody @Valid AddCommentDTO inputDto) {
        return commentService.createByAdmin(inputDto);
    }
}
