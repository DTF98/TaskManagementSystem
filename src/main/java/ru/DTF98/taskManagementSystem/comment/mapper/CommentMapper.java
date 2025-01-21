package ru.DTF98.taskManagementSystem.comment.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.DTF98.taskManagementSystem.auth.model.User;
import ru.DTF98.taskManagementSystem.comment.dto.AddCommentDTO;
import ru.DTF98.taskManagementSystem.comment.model.Comment;
import ru.DTF98.taskManagementSystem.comment.model.CommentDTO;
import ru.DTF98.taskManagementSystem.task.dto.TaskDTO;
import ru.DTF98.taskManagementSystem.task.model.Task;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    CommentDTO toDTO(Comment comment);

    List<CommentDTO> toListDTO(List<Comment> comment);

    @Mapping(target = "task", source = "task")
//    @Mapping(target = "author", source = "author")
//    @Mapping(target = "id", source = "commentDto.id")
    Comment toModel(String comment, User author, Task task);
}
