package antifraud.DTO;

public class UsernameStatus {
    private String username;
    private String status;

    public UsernameStatus(String username, String status) {
        this.username = username;
        this.status = status;
    }

    public UsernameStatus() {
    }

    public String getUsername() {
        return username;
    }

    public String getStatus() {
        return status;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
