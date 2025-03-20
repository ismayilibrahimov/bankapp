package bankapp.user;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    @GetMapping("/profile")
    public ResponseEntity<User> profile(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(user);
    }
}
