package TaskTracker.example.Task.Tracker.Controller;

import TaskTracker.example.Task.Tracker.Exception.UserAlreadyExistsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;

import TaskTracker.example.Task.Tracker.DTO.LoginRequest;
import TaskTracker.example.Task.Tracker.DTO.SignupRequest;
import TaskTracker.example.Task.Tracker.Service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<Map<String, String>> register(@RequestBody SignupRequest request) {
        Map<String, String> response = new HashMap<>();
        try {
            String message = authService.registerUser(request);
            response.put("message", message);
            return ResponseEntity.ok(response);
        } catch (UserAlreadyExistsException e) {
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        } catch (Exception e) {
            response.put("error", "Signup failed due to an internal error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }



    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.authenticateUser(request));
    }
}
