package antifraud.DTOResponses;

import antifraud.config.TransactionResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResultResponse {

    private TransactionResult result;
    private String info;
}
