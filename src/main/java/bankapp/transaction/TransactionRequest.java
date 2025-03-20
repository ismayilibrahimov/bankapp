package bankapp.transaction;

import lombok.*;
import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRequest {
    private String accountNumber;
    private BigDecimal amount;
}
