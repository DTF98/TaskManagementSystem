package ru.DTF98.taskManagementSystem.task.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.DTF98.taskManagementSystem.auth.dto.UserDTO;
import ru.DTF98.taskManagementSystem.comment.model.Comment;
import ru.DTF98.taskManagementSystem.comment.model.CommentDTO;
import ru.DTF98.taskManagementSystem.task.model.Priority;
import ru.DTF98.taskManagementSystem.task.model.Status;

import java.util.List;

@Data
@NoArgsConstructor
public class TaskWithCommentsDTO {
    private Long taskId;

    private String title;

    private String description;

    private Status status;

    private Priority priority;

    private UserDTO author;

    private UserDTO assignee;

    private List<CommentDTO> comments;
}
