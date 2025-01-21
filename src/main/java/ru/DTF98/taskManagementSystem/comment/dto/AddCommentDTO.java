package ru.DTF98.taskManagementSystem.comment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Schema(description = "Запрос на пользователя/админимтратора на добавление комментария к задаче")
public class AddCommentDTO {
    @NotNull(message = "Идентификатор задачи для изменения не может быть пустым")
    @Schema(description = "Идентификатор задачи для комментария")
    private Long taskId;

    @Schema(description = "Коментарий пользователя", example = "задача - огонь!")
    @NotBlank(message = "Комментарий не может быть пустыми")
    private String comment;
}
