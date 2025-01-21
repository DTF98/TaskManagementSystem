package ru.DTF98.taskManagementSystem.task.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.DTF98.taskManagementSystem.auth.dto.UserDTO;
import ru.DTF98.taskManagementSystem.auth.model.User;
import ru.DTF98.taskManagementSystem.comment.model.CommentDTO;
import ru.DTF98.taskManagementSystem.task.dto.CreateTaskDTO;
import ru.DTF98.taskManagementSystem.task.dto.TaskDTO;
import ru.DTF98.taskManagementSystem.task.dto.TaskWithCommentsDTO;
import ru.DTF98.taskManagementSystem.task.dto.UpdateAdminTaskDTO;
import ru.DTF98.taskManagementSystem.task.model.Status;
import ru.DTF98.taskManagementSystem.task.model.Task;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    Task toModel(CreateTaskDTO createTaskDTO, User author, Status status);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Task updateModel(@MappingTarget Task task, UpdateAdminTaskDTO updateAdminTaskDTO);

    TaskDTO toDTO(Task task);

    TaskWithCommentsDTO toDTOWithComments(Task task, List<CommentDTO> comments);

    UserDTO toUserDTO(User user);
}
