package TaskTracker.example.Task.Tracker.Controller;

import TaskTracker.example.Task.Tracker.DTO.LoginRequest;
import TaskTracker.example.Task.Tracker.DTO.SignupRequest;
import TaskTracker.example.Task.Tracker.Service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = {
        "https://task-management-frontend-git-main-sherrywahlas-projects.vercel.app",
        "http://localhost:3000"
})
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<Map<String, String>> register(@RequestBody SignupRequest request) {
        String message = authService.registerUser(request);
        Map<String, String> response = new HashMap<>();
        response.put("message", message);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.authenticateUser(request));
    }
}
