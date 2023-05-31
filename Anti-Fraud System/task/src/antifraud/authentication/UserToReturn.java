package antifraud.authentication;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

public class UserToReturn {
    private Long id;
    private String name;
    private String username;

    public UserToReturn(Long id, String name, String username) {
        this.id = id;
        this.name = name;
        this.username = username;
    }

    public UserToReturn() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
