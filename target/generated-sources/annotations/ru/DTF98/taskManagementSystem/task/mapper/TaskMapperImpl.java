package ru.DTF98.taskManagementSystem.task.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ru.DTF98.taskManagementSystem.auth.dto.UserDTO;
import ru.DTF98.taskManagementSystem.auth.model.User;
import ru.DTF98.taskManagementSystem.comment.model.CommentDTO;
import ru.DTF98.taskManagementSystem.task.dto.CreateTaskDTO;
import ru.DTF98.taskManagementSystem.task.dto.TaskDTO;
import ru.DTF98.taskManagementSystem.task.dto.TaskWithCommentsDTO;
import ru.DTF98.taskManagementSystem.task.dto.UpdateAdminTaskDTO;
import ru.DTF98.taskManagementSystem.task.model.Status;
import ru.DTF98.taskManagementSystem.task.model.Task;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-21T06:38:42+0500",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21 (Oracle Corporation)"
)
@Component
public class TaskMapperImpl implements TaskMapper {

    @Override
    public Task toModel(CreateTaskDTO createTaskDTO, User author, Status status) {
        if ( createTaskDTO == null && author == null && status == null ) {
            return null;
        }

        Task.TaskBuilder task = Task.builder();

        if ( createTaskDTO != null ) {
            task.title( createTaskDTO.getTitle() );
            task.description( createTaskDTO.getDescription() );
            task.priority( createTaskDTO.getPriority() );
            task.assignee( userDTOToUser( createTaskDTO.getAssignee() ) );
        }
        task.author( author );
        task.status( status );

        return task.build();
    }

    @Override
    public Task updateModel(Task task, UpdateAdminTaskDTO updateAdminTaskDTO) {
        if ( updateAdminTaskDTO == null ) {
            return task;
        }

        if ( updateAdminTaskDTO.getTaskId() != null ) {
            task.setTaskId( updateAdminTaskDTO.getTaskId() );
        }
        if ( updateAdminTaskDTO.getTitle() != null ) {
            task.setTitle( updateAdminTaskDTO.getTitle() );
        }
        if ( updateAdminTaskDTO.getDescription() != null ) {
            task.setDescription( updateAdminTaskDTO.getDescription() );
        }
        if ( updateAdminTaskDTO.getStatus() != null ) {
            task.setStatus( updateAdminTaskDTO.getStatus() );
        }
        if ( updateAdminTaskDTO.getPriority() != null ) {
            task.setPriority( updateAdminTaskDTO.getPriority() );
        }
        if ( updateAdminTaskDTO.getAssignee() != null ) {
            if ( task.getAssignee() == null ) {
                task.setAssignee( User.builder().build() );
            }
            userDTOToUser1( updateAdminTaskDTO.getAssignee(), task.getAssignee() );
        }

        return task;
    }

    @Override
    public TaskDTO toDTO(Task task) {
        if ( task == null ) {
            return null;
        }

        TaskDTO taskDTO = new TaskDTO();

        taskDTO.setTaskId( task.getTaskId() );
        taskDTO.setTitle( task.getTitle() );
        taskDTO.setDescription( task.getDescription() );
        taskDTO.setStatus( task.getStatus() );
        taskDTO.setPriority( task.getPriority() );
        taskDTO.setAuthor( toUserDTO( task.getAuthor() ) );
        taskDTO.setAssignee( toUserDTO( task.getAssignee() ) );

        return taskDTO;
    }

    @Override
    public TaskWithCommentsDTO toDTOWithComments(Task task, List<CommentDTO> comments) {
        if ( task == null && comments == null ) {
            return null;
        }

        TaskWithCommentsDTO taskWithCommentsDTO = new TaskWithCommentsDTO();

        if ( task != null ) {
            taskWithCommentsDTO.setTaskId( task.getTaskId() );
            taskWithCommentsDTO.setTitle( task.getTitle() );
            taskWithCommentsDTO.setDescription( task.getDescription() );
            taskWithCommentsDTO.setStatus( task.getStatus() );
            taskWithCommentsDTO.setPriority( task.getPriority() );
            taskWithCommentsDTO.setAuthor( toUserDTO( task.getAuthor() ) );
            taskWithCommentsDTO.setAssignee( toUserDTO( task.getAssignee() ) );
        }
        List<CommentDTO> list = comments;
        if ( list != null ) {
            taskWithCommentsDTO.setComments( new ArrayList<CommentDTO>( list ) );
        }

        return taskWithCommentsDTO;
    }

    @Override
    public UserDTO toUserDTO(User user) {
        if ( user == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setUserId( user.getUserId() );
        userDTO.setUsername( user.getUsername() );
        userDTO.setRole( user.getRole() );

        return userDTO;
    }

    protected User userDTOToUser(UserDTO userDTO) {
        if ( userDTO == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.userId( userDTO.getUserId() );
        user.username( userDTO.getUsername() );
        user.role( userDTO.getRole() );

        return user.build();
    }

    protected void userDTOToUser1(UserDTO userDTO, User mappingTarget) {
        if ( userDTO == null ) {
            return;
        }

        if ( userDTO.getUserId() != null ) {
            mappingTarget.setUserId( userDTO.getUserId() );
        }
        if ( userDTO.getUsername() != null ) {
            mappingTarget.setUsername( userDTO.getUsername() );
        }
        if ( userDTO.getRole() != null ) {
            mappingTarget.setRole( userDTO.getRole() );
        }
    }
}
