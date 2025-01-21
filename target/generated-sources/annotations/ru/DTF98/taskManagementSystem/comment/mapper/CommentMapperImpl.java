package ru.DTF98.taskManagementSystem.comment.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ru.DTF98.taskManagementSystem.auth.dto.UserDTO;
import ru.DTF98.taskManagementSystem.auth.model.User;
import ru.DTF98.taskManagementSystem.comment.model.Comment;
import ru.DTF98.taskManagementSystem.comment.model.CommentDTO;
import ru.DTF98.taskManagementSystem.task.dto.TaskDTO;
import ru.DTF98.taskManagementSystem.task.model.Task;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-21T06:38:42+0500",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21 (Oracle Corporation)"
)
@Component
public class CommentMapperImpl implements CommentMapper {

    @Override
    public CommentDTO toDTO(Comment comment) {
        if ( comment == null ) {
            return null;
        }

        CommentDTO commentDTO = new CommentDTO();

        commentDTO.setId( comment.getId() );
        commentDTO.setTask( taskToTaskDTO( comment.getTask() ) );
        commentDTO.setAuthor( userToUserDTO( comment.getAuthor() ) );
        commentDTO.setComment( comment.getComment() );

        return commentDTO;
    }

    @Override
    public List<CommentDTO> toListDTO(List<Comment> comment) {
        if ( comment == null ) {
            return null;
        }

        List<CommentDTO> list = new ArrayList<CommentDTO>( comment.size() );
        for ( Comment comment1 : comment ) {
            list.add( toDTO( comment1 ) );
        }

        return list;
    }

    @Override
    public Comment toModel(String comment, User author, Task task) {
        if ( comment == null && author == null && task == null ) {
            return null;
        }

        Comment.CommentBuilder comment1 = Comment.builder();

        if ( task != null ) {
            comment1.task( task );
            comment1.author( task.getAuthor() );
        }
        comment1.comment( comment );

        return comment1.build();
    }

    protected UserDTO userToUserDTO(User user) {
        if ( user == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setUserId( user.getUserId() );
        userDTO.setUsername( user.getUsername() );
        userDTO.setRole( user.getRole() );

        return userDTO;
    }

    protected TaskDTO taskToTaskDTO(Task task) {
        if ( task == null ) {
            return null;
        }

        TaskDTO taskDTO = new TaskDTO();

        taskDTO.setTaskId( task.getTaskId() );
        taskDTO.setTitle( task.getTitle() );
        taskDTO.setDescription( task.getDescription() );
        taskDTO.setStatus( task.getStatus() );
        taskDTO.setPriority( task.getPriority() );
        taskDTO.setAuthor( userToUserDTO( task.getAuthor() ) );
        taskDTO.setAssignee( userToUserDTO( task.getAssignee() ) );

        return taskDTO;
    }
}
