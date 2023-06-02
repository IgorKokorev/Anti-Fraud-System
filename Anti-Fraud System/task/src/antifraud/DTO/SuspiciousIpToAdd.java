package antifraud.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SuspiciousIpToAdd {
    private String ip;
}
