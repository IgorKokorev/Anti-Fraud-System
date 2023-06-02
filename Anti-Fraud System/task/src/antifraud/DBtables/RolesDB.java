package antifraud.DBtables;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.LinkedHashSet;
import java.util.Set;

/*
@Entity
@Data
@NoArgsConstructor
@Table(name = "roles")
public class RolesDB {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "role")
    @NotNull
    private String role;

    @ManyToMany(mappedBy = "peopleInContact")
    private Set<UserDB> usersWithRole = new LinkedHashSet<>();

    public RolesDB(String role) {
        this.role = role;
    }
}
*/
