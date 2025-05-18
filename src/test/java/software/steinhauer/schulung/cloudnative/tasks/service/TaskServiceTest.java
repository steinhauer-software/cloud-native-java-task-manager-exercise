package software.steinhauer.schulung.cloudnative.tasks.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import software.steinhauer.schulung.cloudnative.tasks.model.Task;
import software.steinhauer.schulung.cloudnative.tasks.repository.TaskRepository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    private Task task1;
    private Task task2;

    @BeforeEach
    void setUp() {
        task1 = Task.builder()
                .id(1L)
                .title("Test Task 1")
                .description("Test Description 1")
                .completed(false)
                .createdAt(LocalDateTime.now())
                .dueDate(LocalDateTime.now().plusDays(7))
                .priority(Task.Priority.HIGH)
                .build();

        task2 = Task.builder()
                .id(2L)
                .title("Test Task 2")
                .description("Test Description 2")
                .completed(true)
                .createdAt(LocalDateTime.now())
                .dueDate(LocalDateTime.now().plusDays(3))
                .priority(Task.Priority.MEDIUM)
                .build();
    }

    @Test
    void getAllTasks() {
        when(taskRepository.findAll()).thenReturn(Arrays.asList(task1, task2));

        List<Task> tasks = taskService.getAllTasks();

        assertEquals(2, tasks.size());
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    void getTaskById() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task1));

        Task found = taskService.getTaskById(1L);

        assertEquals(task1.getId(), found.getId());
        assertEquals(task1.getTitle(), found.getTitle());
        verify(taskRepository, times(1)).findById(1L);
    }

    @Test
    void getTaskById_notFound() {
        when(taskRepository.findById(3L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> taskService.getTaskById(3L));
        verify(taskRepository, times(1)).findById(3L);
    }

    @Test
    void getTasksByStatus() {
        when(taskRepository.findByCompleted(true)).thenReturn(List.of(task2));

        List<Task> tasks = taskService.getTasksByStatus(true);

        assertEquals(1, tasks.size());
        assertTrue(tasks.getFirst().isCompleted());
        verify(taskRepository, times(1)).findByCompleted(true);
    }

    @Test
    void createTask() {
        when(taskRepository.save(any(Task.class))).thenReturn(task1);

        Task created = taskService.createTask(task1);

        assertEquals(task1.getTitle(), created.getTitle());
        verify(taskRepository, times(1)).save(task1);
    }

    @Test
    void updateTask() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task1));
        when(taskRepository.save(any(Task.class))).thenReturn(task1);

        Task updated = Task.builder()
                .title("Updated Task")
                .description("Updated Description")
                .completed(true)
                .dueDate(LocalDateTime.now().plusDays(10))
                .priority(Task.Priority.LOW)
                .build();

        Task result = taskService.updateTask(1L, updated);

        assertEquals(updated.getTitle(), result.getTitle());
        assertEquals(updated.getDescription(), result.getDescription());
        assertEquals(updated.isCompleted(), result.isCompleted());
        verify(taskRepository, times(1)).findById(1L);
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    void markTaskAsCompleted() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task1));
        when(taskRepository.save(any(Task.class))).thenAnswer(invocation -> {
            Task savedTask = invocation.getArgument(0);
            assertTrue(savedTask.isCompleted());
            return savedTask;
        });

        Task completed = taskService.markTaskAsCompleted(1L);

        assertTrue(completed.isCompleted());
        verify(taskRepository, times(1)).findById(1L);
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    void deleteTask() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task1));
        doNothing().when(taskRepository).delete(any(Task.class));

        taskService.deleteTask(1L);

        verify(taskRepository, times(1)).findById(1L);
        verify(taskRepository, times(1)).delete(task1);
    }
}
