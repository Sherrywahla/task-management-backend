package TaskTracker.example.Task.Tracker.Service;

import TaskTracker.example.Task.Tracker.Entities.Task;
import TaskTracker.example.Task.Tracker.Entities.User;
import TaskTracker.example.Task.Tracker.Repository.TaskRepository;
import TaskTracker.example.Task.Tracker.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public Task createTask(Task task, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));
        task.setUser(user);
        return taskRepository.save(task);
    }

    public List<Task> getAllTasks(String userEmail, boolean isAdmin) {
        if (isAdmin) {
            return taskRepository.findAll();
        }
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return taskRepository.findByUserId(user.getId());
    }

    public Task updateTask(Long id, Task updatedTask, String userEmail) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        if (task.getUser() == null) {
            throw new RuntimeException("Task owner is null");
        }

        System.out.println("Task belongs to: " + task.getUser().getEmail());

        if (!task.getUser().getEmail().equals(userEmail)) {
            throw new RuntimeException("Unauthorized to update this task");
        }

        task.setTitle(updatedTask.getTitle());
        task.setDescription(updatedTask.getDescription());
        task.setDueDate(updatedTask.getDueDate());
        task.setPriority(updatedTask.getPriority());
        task.setStatus(updatedTask.getStatus());
        return taskRepository.save(task);
    }


    public void deleteTask(Long id, String userEmail, boolean isAdmin) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        if (!isAdmin && !task.getUser().getEmail().equals(userEmail)) {
            throw new RuntimeException("Unauthorized to delete this task");
        }

        taskRepository.delete(task);
    }

}
