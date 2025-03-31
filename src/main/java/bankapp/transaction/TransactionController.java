package bankapp.transaction;

import bankapp.account.Account;
import bankapp.account.AccountService;
import bankapp.user.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
public class TransactionController {
    private final AccountService accountService;
    private final TransactionService transactionService;

    @PostMapping("/deposit")
    public ResponseEntity<Transaction> deposit(
            @AuthenticationPrincipal User user, @Valid @RequestBody TransactionRequest request) {
        Account account = accountService.getUserOwnedAccount(user.getUsername(), request.getAccountNumber());
        return ResponseEntity.ok(transactionService.deposit(account.getAccountNumber(), request.getAmount()));
    }

    @PostMapping("/withdraw")
    public ResponseEntity<Transaction> withdraw(
            @AuthenticationPrincipal User user, @Valid @RequestBody TransactionRequest request) {
        Account account = accountService.getUserOwnedAccount(user.getUsername(), request.getAccountNumber());
        return ResponseEntity.ok(transactionService.withdraw(account.getAccountNumber(), request.getAmount()));
    }

    @PostMapping("/transfer")
    public ResponseEntity<Transaction> transfer(
            @AuthenticationPrincipal User user, @Valid @RequestBody TransferRequest request) {
        Account fromAccount = accountService.getUserOwnedAccount(user.getUsername(), request.getFromAccountNumber());
        return ResponseEntity.ok(transactionService.transfer(
                fromAccount.getAccountNumber(), request.getToAccountNumber(), request.getAmount()));
    }
}
