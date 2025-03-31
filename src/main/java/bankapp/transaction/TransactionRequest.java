package bankapp.transaction;

import jakarta.validation.constraints.DecimalMin;
import lombok.*;
import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRequest {
    private String accountNumber;

    @DecimalMin(value = "0.01", message = "Amount must be greater than 0.01")
    private BigDecimal amount;
}
