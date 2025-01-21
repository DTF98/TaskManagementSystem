package ru.DTF98.taskManagementSystem.task.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.DTF98.taskManagementSystem.task.model.Status;

@Data
@NoArgsConstructor
@Schema(description = "Запрос пользователя на изменнение задачи")
public class UpdateUserTaskDTO {
    @NotNull(message = "Идентификатор задачи для изменения не может быть пустым")
    @Schema(description = "Идентификатор задачи для изменения",example = "1")
    private Long taskId;

    @Schema(description = "Статус задачи", example = "IN_WAITING, IN_PROGRESS, COMPLETED")
    @NotNull(message = "Комментарий не может быть пустыми")
    private Status status;
}
