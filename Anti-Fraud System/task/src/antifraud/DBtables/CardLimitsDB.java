package antifraud.DBtables;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "card_limits")
public class CardLimitsDB {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "number", unique = true, nullable = false)
    private String number;

    @Column(name = "max_allowed", nullable = false)
    private long maxAllowed = 200L;

    @Column(name = "max_manual", nullable = false)
    private long maxManual = 1500L;

    public CardLimitsDB(String number) {
        this.number = number;
    }
}
