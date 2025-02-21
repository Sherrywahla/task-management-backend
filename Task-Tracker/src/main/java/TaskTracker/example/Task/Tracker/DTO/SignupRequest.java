package TaskTracker.example.Task.Tracker.DTO;
import TaskTracker.example.Task.Tracker.constants.Role;


import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class SignupRequest {
    private String email;
    private String password;
    private Role role;
}
