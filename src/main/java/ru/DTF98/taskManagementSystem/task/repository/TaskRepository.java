package ru.DTF98.taskManagementSystem.task.repository;

import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.DTF98.taskManagementSystem.task.model.Task;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    Optional<Task> findById(@NonNull Long id);

    @Query(value = "SELECT * FROM tasks WHERE author_id = :authorId", nativeQuery = true)
    Page<Task> findByAuthorId(@Param("authorId") Long authorId, Pageable pageable);

    @Query(value = "SELECT * FROM tasks WHERE assignee_id = :assigneeId", nativeQuery = true)
    Page<Task> findByAssigneeId(@Param("assigneeId") Long assigneeId, Pageable pageable);

    @Query(value = "SELECT * FROM tasks WHERE author_id = :authorId", nativeQuery = true)
    List<Task> findByAuthorId(@Param("authorId") Long authorId);
}
