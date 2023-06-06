package antifraud.DTORequests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionToAdd {
    private long amount;
    private String ip;
    private String number;
    private String region;
    private LocalDateTime date;
}
