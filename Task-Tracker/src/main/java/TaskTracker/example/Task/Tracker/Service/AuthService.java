package TaskTracker.example.Task.Tracker.Service;
import TaskTracker.example.Task.Tracker.Exception.UserAlreadyExistsException;


import TaskTracker.example.Task.Tracker.DTO.LoginRequest;
import TaskTracker.example.Task.Tracker.DTO.SignupRequest;
import TaskTracker.example.Task.Tracker.Entities.User;
import TaskTracker.example.Task.Tracker.Repository.UserRepository;
import TaskTracker.example.Task.Tracker.constants.Role;
import TaskTracker.example.Task.Tracker.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    public String registerUser(SignupRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already taken");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        user.setRole(request.getRole());

        userRepository.save(user);
        return "User registered successfully!";
    }


    public String authenticateUser(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return jwtUtils.generateJwtToken(userDetails);
    }
}
