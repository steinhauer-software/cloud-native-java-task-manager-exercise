package software.steinhauer.schulung.cloudnative.tasks.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import software.steinhauer.schulung.cloudnative.tasks.model.Task;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByCompleted(boolean completed);

    List<Task> findByDueDateBefore(LocalDateTime date);

    List<Task> findByPriority(Task.Priority priority);
}
