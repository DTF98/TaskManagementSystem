package ru.DTF98.taskManagementSystem.task.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.DTF98.taskManagementSystem.auth.dto.UserDTO;
import ru.DTF98.taskManagementSystem.task.model.Priority;
import ru.DTF98.taskManagementSystem.task.model.Status;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDTO {
    private Long taskId;

    private String title;

    private String description;

    private Status status;

    private Priority priority;

    private UserDTO author;

    private UserDTO assignee;
}
