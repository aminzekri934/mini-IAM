import java.util.HashSet;
import java.util.Set;

public class User {
    private int userId;
    private String username;
    private String department;
    private boolean isActive;

    // Hier ist die Verknüpfung zu den Rollen!
    private Set<Role> roles = new HashSet<>();

    // Konstruktor
    public User(int userId, String username, String department, boolean isActive) {
        this.userId = userId;
        this.username = username;
        this.department = department;
        this.isActive = isActive;
    }

    // Getter und Setter würden hier folgen...
    public boolean isActive() {
        return isActive;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
