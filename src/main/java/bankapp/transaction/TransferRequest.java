package bankapp.transaction;

import jakarta.validation.constraints.DecimalMin;
import lombok.*;
import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TransferRequest {
    private String fromAccountNumber;
    private String toAccountNumber;

    @DecimalMin(value = "0.01", message = "Amount must be greater than 0.01")
    private BigDecimal amount;
}
