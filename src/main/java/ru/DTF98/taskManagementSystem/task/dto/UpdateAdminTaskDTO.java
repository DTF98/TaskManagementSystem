package ru.DTF98.taskManagementSystem.task.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.DTF98.taskManagementSystem.auth.dto.UserDTO;
import ru.DTF98.taskManagementSystem.auth.model.User;
import ru.DTF98.taskManagementSystem.task.model.Priority;
import ru.DTF98.taskManagementSystem.task.model.Status;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Запрос адмсинистратора на обновление задачи")
public class UpdateAdminTaskDTO {
    @NotNull(message = "Идентификатор задачи для изменения не может быть пустым")
    @Schema(description = "Идентификатор задачи для изменения",example = "1")
    private Long taskId;

    @Schema(description = "Название задачи", example = "Построить дачу")
    @NotBlank(message = "Название задачи не может быть пустыми")
    private String title;

    @Schema(description = "Описание задачи", example = "Хочу 2-ух этажную, с бассейном")
    @NotBlank(message = "Описание задачи не может быть пустыми")
    private String description;

    @Schema(description = "Статус задачаи", example = "IN_WAITING, IN_PROGRESS, COMPLETED")
    @NotNull(message = "Статус задачаи не может быть пустыми")
    private Status status;

    @Schema(description = "Приоритет задачи", example = "HIGH, AVERAGE, LOW")
    @NotNull(message = "Приоритет задачи для изменения не может быть пустым")
    private Priority priority;

    @Schema(description = "Исполнитель задачи")
    private UserDTO assignee;
}
