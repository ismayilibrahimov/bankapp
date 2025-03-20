package bankapp.account;

import bankapp.user.User;
import bankapp.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    public Account createAccount(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Account account = new Account();
        account.setAccountNumber(UUID.randomUUID().toString());
        account.setUser(user);
        account.setBalance(BigDecimal.ZERO);

        return accountRepository.save(account);
    }

    public List<Account> getUserAccounts(String username) {
        return accountRepository.findByUserUsername(username);
    }

    public Account getUserOwnedAccount(String username, String accountNumber) {
        return accountRepository.findByAccountNumberAndUserUsername(accountNumber, username)
                .orElseThrow(() -> new RuntimeException("Access denied: Account not found or not owned by user"));
    }
}


