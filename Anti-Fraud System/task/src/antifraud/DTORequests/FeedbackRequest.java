package antifraud.DTORequests;

import antifraud.config.TransactionResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackRequest {
    private long transactionId;
    private TransactionResult feedback;
}
