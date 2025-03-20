package bankapp.transaction;

import lombok.*;
import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TransferRequest {
    private String fromAccountNumber;
    private String toAccountNumber;
    private BigDecimal amount;
}
