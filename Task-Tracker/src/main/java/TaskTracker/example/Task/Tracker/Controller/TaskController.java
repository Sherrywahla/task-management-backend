package TaskTracker.example.Task.Tracker.Controller;

import TaskTracker.example.Task.Tracker.Entities.Task;
import TaskTracker.example.Task.Tracker.Service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task,
                                           @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(taskService.createTask(task, userDetails.getUsername()));
    }

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks(@AuthenticationPrincipal UserDetails userDetails) {
        boolean isAdmin = userDetails.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));
        return ResponseEntity.ok(taskService.getAllTasks(userDetails.getUsername(), isAdmin));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task task,
                                           @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(taskService.updateTask(id, task, userDetails.getUsername()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable Long id,
                                             @AuthenticationPrincipal UserDetails userDetails) {
        boolean isAdmin = userDetails.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));
        taskService.deleteTask(id, userDetails.getUsername(), isAdmin);
        return ResponseEntity.ok("Task deleted successfully!");
    }


}
