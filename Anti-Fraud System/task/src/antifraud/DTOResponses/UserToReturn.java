package antifraud.DTOResponses;

import antifraud.DBtables.UserDB;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserToReturn {
    private Long id;
    private String name;
    private String username;
    private String role;

    public UserToReturn(UserDB userDB) {
        this.id = userDB.getId();
        this.name = userDB.getName();
        this.username = userDB.getUsername();
        this.role = userDB.getRole();
    }
}
