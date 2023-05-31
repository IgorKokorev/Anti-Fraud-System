package antifraud.transaction;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class AntiFraudController {
    @PostMapping(value = "/api/antifraud/transaction")
    public TransactionResult makeTransaction(@RequestBody Transaction transaction) {
        if (transaction.getAmount() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Amount should be a positive long number");
        }
        if (transaction.getAmount() > 1500) return new TransactionResult(TransactionResult.Result.PROHIBITED);
        if (transaction.getAmount() <= 200) return new TransactionResult(TransactionResult.Result.ALLOWED);
        return new TransactionResult(TransactionResult.Result.MANUAL_PROCESSING);
    }
}
