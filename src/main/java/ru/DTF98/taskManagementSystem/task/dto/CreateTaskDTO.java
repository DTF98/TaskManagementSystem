package ru.DTF98.taskManagementSystem.task.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.DTF98.taskManagementSystem.auth.dto.UserDTO;
import ru.DTF98.taskManagementSystem.auth.model.User;
import ru.DTF98.taskManagementSystem.task.model.Priority;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Запрос адмсинистратора на создание задачи")
public class CreateTaskDTO {
    @Schema(description = "Название задачи", example = "Построить дачу")
    @NotBlank(message = "Email пользователя не может быть пустыми")
    private String title;

    @Schema(description = "Описание задачи", example = "Хочу 2-ух этажную, с бассейном")
    @NotBlank(message = "Email пользователя не может быть пустыми")
    private String description;

    @Schema(description = "Приоритет задачи", example = "HIGH, AVERAGE, LOW")
    private Priority priority;

    @Schema(description = "Исполнитель задачи")
    private UserDTO assignee;
}
