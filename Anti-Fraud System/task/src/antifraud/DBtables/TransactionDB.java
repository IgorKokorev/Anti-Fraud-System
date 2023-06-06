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
    private Long id;

    @Column(name = "amount")
    @NotNull
    private long amount;

    @Column(name = "ip")
    @NotNull
    private String ip;

    @Column(name = "number")
    @NotNull
    private String number;

    @Column(name = "region")
    @NotNull
    private String region;

    @Column(name = "date")
    @NotNull
    private LocalDateTime date;

    @Column(name = "result")
    @NotNull
    private TransactionResult result;

    @Column(name = "info")
    @NotNull
    private String info;

    public TransactionDB(TransactionToAdd tr, TransactionResult result, String info) {
        this.amount = tr.getAmount();
        this.ip = tr.getIp();
        this.number = tr.getNumber();
        this.region = tr.getRegion();
        this.date = tr.getDate();
        this.result = result;
        this.info = info;
    }
}
