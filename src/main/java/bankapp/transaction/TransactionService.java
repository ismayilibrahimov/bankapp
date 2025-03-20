package bankapp.transaction;

import bankapp.account.Account;
import bankapp.account.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    @Transactional
    public Transaction deposit(String accountNumber, BigDecimal amount) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        account.setBalance(account.getBalance().add(amount));
        accountRepository.save(account);

        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setAmount(amount);
        transaction.setType(TransactionType.DEPOSIT);

        return transactionRepository.save(transaction);
    }

    @Transactional
    public Transaction withdraw(String accountNumber, BigDecimal amount) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        if (account.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient balance");
        }

        account.setBalance(account.getBalance().subtract(amount));
        accountRepository.save(account);

        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setAmount(amount);
        transaction.setType(TransactionType.WITHDRAW);

        return transactionRepository.save(transaction);
    }

    @Transactional
    public Transaction transfer(String fromAccountNumber, String toAccountNumber, BigDecimal amount) {
        Account fromAccount = accountRepository.findByAccountNumber(fromAccountNumber)
                .orElseThrow(() -> new RuntimeException("Source account not found"));

        Account toAccount = accountRepository.findByAccountNumber(toAccountNumber)
                .orElseThrow(() -> new RuntimeException("Destination account not found"));

        if (fromAccount.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient balance");
        }

        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        toAccount.setBalance(toAccount.getBalance().add(amount));

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

        Transaction transaction = new Transaction();
        transaction.setAccount(fromAccount);
        transaction.setAmount(amount);
        transaction.setType(TransactionType.TRANSFER);

        return transactionRepository.save(transaction);
    }
}
