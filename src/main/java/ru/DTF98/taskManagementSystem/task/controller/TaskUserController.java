package ru.DTF98.taskManagementSystem.task.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.DTF98.taskManagementSystem.comment.dto.AddCommentDTO;
import ru.DTF98.taskManagementSystem.comment.model.CommentDTO;
import ru.DTF98.taskManagementSystem.comment.service.CommentService;
import ru.DTF98.taskManagementSystem.task.dto.TaskDTO;
import ru.DTF98.taskManagementSystem.task.dto.TaskWithCommentsDTO;
import ru.DTF98.taskManagementSystem.task.dto.UpdateUserTaskDTO;
import ru.DTF98.taskManagementSystem.task.service.TaskUserService;

@RestController
@RequestMapping("/user/resource")
@RequiredArgsConstructor
@Tag(name = "Пользовательский контролер")
@Validated
public class TaskUserController {
    private final TaskUserService taskUserService;
    private final CommentService commentService;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Получить задачу, если пользователь является исполнителем и он зарегистрирован")
    public TaskWithCommentsDTO getById(@PathVariable Long id) {
        return taskUserService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Обновить статус задачи, если пользователь является исполнителем и он зарегистрирован")
    public TaskDTO updateStatus(@RequestBody @Valid UpdateUserTaskDTO inputDto) {
        return taskUserService.updateStatus(inputDto);
    }

    @PutMapping("/comment")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Добавить комментарий, если пользователь является исполнителем и он зарегистрирован")
    public CommentDTO addComment(@RequestBody @Valid AddCommentDTO inputDto) {
        return commentService.createByUser(inputDto);
    }
}
