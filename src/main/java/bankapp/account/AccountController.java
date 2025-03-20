package bankapp.account;

import bankapp.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @PostMapping("/create")
    public ResponseEntity<Account> createAccount(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(accountService.createAccount(user.getUsername()));
    }

    @GetMapping("")
    public ResponseEntity<List<Account>> getUserAccounts(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(accountService.getUserAccounts(user.getUsername()));
    }
}
