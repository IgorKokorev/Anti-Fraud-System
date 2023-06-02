package antifraud.DBtables;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@Table(name = "users")
public class UserDB {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    @NotNull
    private String name;
    @Column(name = "username")
    @NotNull
    private String username;
    @Column(name = "password")
    @NotNull
    private String password;
    @Column(name = "isNonLocked")
    @NotNull
    private boolean isAccountNonLocked;
    @Column(name = "role")
    @NotNull
    private String role;


/*    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RolesDB> rolesOfUser = new LinkedHashSet<>();*/

    public UserDB(String name, String username, String password, boolean isAccountNonLocked, String role) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.isAccountNonLocked = isAccountNonLocked;
        this.role = role;
    }
}
