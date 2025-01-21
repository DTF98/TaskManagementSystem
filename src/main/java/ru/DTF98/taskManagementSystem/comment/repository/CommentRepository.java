package ru.DTF98.taskManagementSystem.comment.repository;

import lombok.NonNull;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.DTF98.taskManagementSystem.comment.model.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findById(@NonNull Long id);

    @Query(value = "SELECT * FROM comments WHERE task_id = :taskId", nativeQuery = true)
    List<Comment> findByTaskId(@Param("taskId") Long taskId, Pageable pageable);

    @Query(value = "SELECT * FROM comments WHERE task_id = :taskId", nativeQuery = true)
    List<Comment> findByTaskId(@Param("taskId") Long taskId);
}
