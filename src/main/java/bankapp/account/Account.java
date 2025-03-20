package bankapp.account;

import bankapp.user.User;
import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;


@Entity
@Data
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String accountNumber;

    @Column(nullable = false)
    private BigDecimal balance = BigDecimal.ZERO;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
