import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SecurityAuditor {

    private static final String DB_URL = ConfigReader.getProperty("db.url");
    private static final String DB_USER = ConfigReader.getProperty("db.user");
    private static final String DB_PASSWORD = ConfigReader.getProperty("db.password");

    /**
     * Findet alle inaktiven User, die fälschlicherweise noch Rollen besitzen.
     */
    public List<String> findOrphanedAccounts() {
        List<String> orphanedUsers = new ArrayList<>();
        String sql = "SELECT DISTINCT username FROM users \n"+
                     "JOIN user_roles ON users.user_id = user_roles.user_id \n"+
                     "WHERE users.is_active = FALSE ;";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                orphanedUsers.add(rs.getString("username"));
            }

        } catch (Exception e) {
            System.err.println("Fehler beim Audit-Scan: " + e.getMessage());
        }

        return orphanedUsers;
    }
    /**
     * Findet User, die zwei sich ausschließende Rollen gleichzeitig besitzen (SoD Konflikt).
     */
    public List<String> checkSoDConflict(int roleIdA, int roleIdB) {
        List<String> conflictUsers = new ArrayList<>();

        String sql = "SELECT DISTINCT username FROM users \n"+
                     "JOIN user_roles  user_roles_A ON users.user_id = user_roles_A.user_id\n"+
                     "JOIN user_roles user_roles_B ON users.user_id = user_roles_B.user_id\n"+
                     "WHERE user_roles_A.role_id = ? AND user_roles_B.role_id = ? ;";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, roleIdA);
            pstmt.setInt(2, roleIdB);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    conflictUsers.add(rs.getString("username"));
                }
            }

        } catch (Exception e) {
            System.err.println("Fehler beim SoD-Scan: " + e.getMessage());
        }

        return conflictUsers;
    }
}