package antifraud.DBtables;

import antifraud.DTORequests.TransactionToAdd;
import antifraud.config.TransactionResult;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Table(name = "transactions")
public class TransactionDB {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long transactionId;

    @Column(name = "amount", nullable = false)
    private long amount;

    @Column(name = "ip", nullable = false)
    private String ip;

    @Column(name = "number", nullable = false)
    private String number;

    @Column(name = "region", nullable = false)
    private String region;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    @Column(name = "result", nullable = false)
    private TransactionResult result;

    @Column(name = "feedback")
    private String feedback;

    public TransactionDB(TransactionToAdd tr, TransactionResult result) {
        this.amount = tr.getAmount();
        this.ip = tr.getIp();
        this.number = tr.getNumber();
        this.region = tr.getRegion();
        this.date = tr.getDate();
        this.result = result;
        this.feedback = "";
    }
}
